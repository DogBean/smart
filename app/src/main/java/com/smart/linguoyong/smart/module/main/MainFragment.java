package com.smart.linguoyong.smart.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smart.linguoyong.data.source.Banner;
import com.smart.linguoyong.data.source.RecommendBean;
import com.smart.linguoyong.data.source.RecommendDailyBean;
import com.smart.linguoyong.smart.R;
import com.smart.linguoyong.smart.app.SmartApplication;
import com.smart.linguoyong.smart.base.RxBus;
import com.smart.linguoyong.smart.base.RxLazyFragment;
import com.smart.linguoyong.smart.module.search.SearchActivity;
import com.smart.linguoyong.smart.utils.Navigator;
import com.smart.linguoyong.smart.view.banner.RegionRecommendBannerSection;
import com.smart.linguoyong.smart.view.section.RegionRecommendDailySection;
import com.smart.linguoyong.smart.view.section.RegionRecommendHotSection;
import com.smart.linguoyong.smart.view.section.RegionRecommendTypesSection;
import com.smart.linguoyong.smart.view.sectioned.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Guoyong.Lin on 2018/12/14
 * 首页模块
 **/
public class MainFragment extends RxLazyFragment implements MainContract.View {
    private static final String TAG = SmartApplication.TAG;

    @BindView(R.id.main_recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.main_swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    private boolean mIsRefreshing = false;

    private List<Banner.BannerEntity> bannerEntities = new ArrayList<>();

    private SectionedRecyclerViewAdapter mSectionedRecyclerViewAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mPresenter != null){
            mPresenter.subscribe();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mSectionedRecyclerViewAdapter.removeAllSections();
        mSectionedRecyclerViewAdapter.notifyDataSetChanged();
        if(mPresenter!=null){
            mPresenter.unsubscribe();
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, root);
        initRecyclerView();
        initRxBus();
        return root;
    }

    @OnClick({R.id.rl_search})
    public void onClick(View view){
        if(view.getId() == R.id.rl_search){
            startActivity(new Intent(getActivity(),SearchActivity.class));
        }
    }
    protected void initRecyclerView() {
        mSectionedRecyclerViewAdapter = new SectionedRecyclerViewAdapter();

        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mSectionedRecyclerViewAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 2;
                    default:
                        return 1;
                }
            }
        });

        mRecyclerView.post(() -> {
            mRefreshLayout.setRefreshing(true);
            mIsRefreshing = true;
        });

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mSectionedRecyclerViewAdapter);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setBannerSection(List<Banner.BannerEntity> bannerEntities) {
        mSectionedRecyclerViewAdapter.addSection(new RegionRecommendBannerSection(bannerEntities));
        mSectionedRecyclerViewAdapter.addSection(new RegionRecommendTypesSection(getActivity(), 0));
        mSectionedRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void setRecommedSection(List<RecommendBean> recommendBeans) {
        mSectionedRecyclerViewAdapter.addSection(new RegionRecommendHotSection(getContext(), 0, recommendBeans));

        List<RecommendDailyBean> arrayList = new ArrayList();
        RecommendDailyBean recommendBean = new RecommendDailyBean("http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg");
        RecommendDailyBean recommendBean2 = new RecommendDailyBean("http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg");
        arrayList.add(recommendBean);
        arrayList.add(recommendBean2);

        mSectionedRecyclerViewAdapter.addSection(new RegionRecommendDailySection(getContext(), 0, arrayList));

        mRefreshLayout.setRefreshing(false);
        mIsRefreshing = false;
        mSectionedRecyclerViewAdapter.notifyDataSetChanged();

    }

    private void initRxBus() {
        // 首页类别 item 点击事件
        RxBus.getInstance().toObserverable(Integer.class)
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::performMainType);
    }

    private void performMainType(int type) {
        Log.e(TAG, "performMainType:" + type);
        Navigator.navigateToSort(getContext(), type);
    }
}
