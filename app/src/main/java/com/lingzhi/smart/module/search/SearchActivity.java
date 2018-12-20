package com.lingzhi.smart.module.search;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.smart.linguoyong.data.bean.SearchResultBean;
import com.lingzhi.smart.R;
import com.lingzhi.smart.base.RxBaseActivity;
import com.smart.linguoyong.data.bean.SearchTagBean;
import com.lingzhi.smart.base.RxBus;
import com.lingzhi.smart.event.AlbumTagEvent;
import com.lingzhi.smart.event.AudioTagEvent;
import com.lingzhi.smart.module.albumresult.AlbumResultsFragment;
import com.lingzhi.smart.module.audioresult.AudioResultsFragment;
import com.lingzhi.smart.module.multiple.MultipleResultFragment;
import com.lingzhi.smart.utils.InputUtils;
import com.lingzhi.smart.utils.ToastUtils;
import com.lingzhi.smart.view.SearchEditText;
import com.lingzhi.smart.view.WrapLinearLayout;
import com.lingzhi.smart.view.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;


public class SearchActivity extends RxBaseActivity implements SearchContract.View{
    @BindView(R.id.search_input_tv)
    SearchEditText searchEditText;
    @BindView(R.id.nearly_search)
    WrapLinearLayout nearlySearch;
    @BindView(R.id.hot_search)
    WrapLinearLayout hotSearch;
    @BindView(R.id.nearly_nodata)
    TextView nearly_nodata;
    @BindView(R.id.hot_nodata)
    TextView hot_nodata;
    @BindView(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.iv_search_loading)
    ImageView mLoadingView;
    @BindView(R.id.search_layout)
    LinearLayout mSearchLayout;
    @BindView(R.id.fl_result)
    FrameLayout fl_result;
    @BindView(R.id.ll_search_tag)
    LinearLayout ll_search_tag;
    NoScrollViewPager mViewPager;
    SearchContract.Presenter mPresenter;
    private SearchResultBean searchResultBean = new SearchResultBean();
    private List<String> titles;
    private List<Fragment> fragments;
    private AnimationDrawable mAnimationDrawable;
    private MultipleResultFragment multipleResultFragment;
    private AlbumResultsFragment albumResultsFragment;
    private AudioResultsFragment audioResultFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mViewPager = findViewById(R.id.view_pager);
        mPresenter = new SearchPresenter(this);
        searchEditText.setOnFocusListener(new SearchEditText.OnFocusListener() {
            @Override
            public void onFocus() {
                showTagLayout();
            }
        });
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String keyWord = searchEditText.getText().toString().trim();
                if (actionId == EditorInfo.IME_ACTION_SEARCH && !TextUtils.isEmpty(keyWord)) {
                    // 当按了搜索之后关闭软键盘
                    InputUtils.hideKeyboard(searchEditText);
                    startSearch(keyWord);
                    return true;
                }
                return false;
            }
        });
        mLoadingView.setImageResource(R.drawable.anim_search_loading);
        mAnimationDrawable = (AnimationDrawable) mLoadingView.getDrawable();
        finishTask();
    }

    @Override
    public void initToolBar() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getSearchHotTag("");
        mPresenter.getSearchNearlyTag();
    }

    @OnClick({R.id.tv_cancle,R.id.clear_nearly_search})
    public void onClick(View view){
        int viewId = view.getId();
        if(viewId == R.id.tv_cancle){
            finish();
        }else if(viewId == R.id.clear_nearly_search){
            mPresenter.clearSearchNearlyTag();
        }
    }

    @Override
    public void showLoading(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void dissmissLoading() {

    }

    @Override
    public void showSearchHotTag(List<SearchTagBean> tags) {
        if(tags == null) {
            return;
        }
        hotSearch.removeAllViews();
        for (SearchTagBean tag : tags) {
            TextView textView = getBaseTextView();
            textView.setText(tag.getElement());
            hotSearch.addView(textView);
            hot_nodata.setVisibility(View.GONE);
        }
    }

    @Override
    public void showSearchNearlyTag(List<String> tags) {
        nearlySearch.removeAllViews();
        for (String tag : tags) {
            TextView textView = getBaseTextView();
            textView.setText(tag);
            nearlySearch.addView(textView);
            nearly_nodata.setVisibility(View.GONE);
        }
    }

    @Override
    public void clearTagView() {
        nearlySearch.removeAllViews();
        nearly_nodata.setVisibility(View.VISIBLE);
    }

    @Override
    public void startSearch(String key) {
        searchEditText.setText(key);
        mPresenter.insertSearchNearlyTag(key);
        mPresenter.search(key);
        showSearchAnim();
    }

    @Override
    public void showSearchResult(SearchResultBean resultBean) {
        if(resultBean == null){
            setEmptyLayout();
        }else {
            hideSearchAnim();
            multipleResultFragment.setResult(resultBean);
        }
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        mPresenter = new SearchPresenter(this);
    }
    private TextView getBaseTextView() {
        TextView textView = new TextView(this);
        textView.setBackgroundResource(R.drawable.search_tv_bg_shape);
        textView.setPadding(10, 10, 10, 10);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                startSearch(tv.getText().toString());
            }
        });
        return textView;
    }
    public void finishTask() {
        titles = new ArrayList<>();
        fragments = new ArrayList<>();
        titles.add("综合");
        titles.add("专辑");
        titles.add("音频");
        multipleResultFragment = new MultipleResultFragment();
        albumResultsFragment = new AlbumResultsFragment();
        audioResultFragment = new AudioResultsFragment();

        fragments.add(multipleResultFragment);
        fragments.add(albumResultsFragment);
        fragments.add(audioResultFragment);

        SearchTabAdapter mAdapter = new SearchTabAdapter(getSupportFragmentManager(), titles, fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(titles.size());
        mSlidingTabLayout.setViewPager(mViewPager);
        measureTabLayoutTextWidth(0);
        mSlidingTabLayout.setCurrentTab(0);
        mAdapter.notifyDataSetChanged();
        mSlidingTabLayout.notifyDataSetChanged();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                measureTabLayoutTextWidth(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    private void measureTabLayoutTextWidth(int position) {
        String title = titles.get(position);
        TextView titleView = mSlidingTabLayout.getTitleView(position);
        TextPaint paint = titleView.getPaint();
        float textWidth = paint.measureText(title);
        mSlidingTabLayout.setIndicatorWidth(textWidth / 3);
    }
    private static class SearchTabAdapter extends FragmentStatePagerAdapter {
        private List<String> titles;
        private List<Fragment> fragments;

        SearchTabAdapter(FragmentManager fm, List<String> titles, List<Fragment> fragments) {
            super(fm);
            this.titles = titles;
            this.fragments = fragments;
        }
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
    private void showTagLayout(){
        fl_result.setVisibility(View.GONE);
        ll_search_tag.setVisibility(View.VISIBLE);
    }
    private void hideTagLayout(){
        fl_result.setVisibility(View.VISIBLE);
        ll_search_tag.setVisibility(View.GONE);
    }
    private void showSearchAnim() {
        hideTagLayout();
        mLoadingView.setVisibility(View.VISIBLE);
        mSearchLayout.setVisibility(View.GONE);
        mAnimationDrawable.start();
    }
    private void hideSearchAnim() {
        mLoadingView.setVisibility(View.GONE);
        mSearchLayout.setVisibility(View.VISIBLE);
        mAnimationDrawable.stop();
    }

    public void setEmptyLayout() {
        mLoadingView.setVisibility(View.VISIBLE);
        mSearchLayout.setVisibility(View.GONE);
        mLoadingView.setImageResource(R.drawable.search_failed);
    }

    @Override
    protected void subscribeRxbus() {
        RxBus.getInstance().toObserverable(AlbumTagEvent.class).subscribe(new Action1<AlbumTagEvent>() {
            @Override
            public void call(AlbumTagEvent albumTagEvent) {
                mViewPager.setCurrentItem(1);
            }
        });
        RxBus.getInstance().toObserverable(AudioTagEvent.class).subscribe(new Action1<AudioTagEvent>() {
            @Override
            public void call(AudioTagEvent albumTagEvent) {
                mViewPager.setCurrentItem(2);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (InputUtils.isShouldHideKeyboard(v, ev)) {
                InputUtils.hideKeyboard(searchEditText);
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
