package com.lingzhi.smart.module.main;

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
import android.widget.Button;

import com.lingzhi.smart.R;
import com.lingzhi.smart.app.SmartApplication;
import com.lingzhi.smart.base.RxBus;
import com.lingzhi.smart.base.RxLazyFragment;
import com.lingzhi.smart.data.bean.DatedLinkGroup;
import com.lingzhi.smart.data.bean.IconLink;
import com.lingzhi.smart.data.bean.ResourceGroup;
import com.lingzhi.smart.data.bean.Song;
import com.lingzhi.smart.data.source.Banner;
import com.lingzhi.smart.data.source.RecommendBean;
import com.lingzhi.smart.module.search.SearchActivity;
import com.lingzhi.smart.utils.Navigator;
import com.lingzhi.smart.view.banner.RegionRecommendBannerSection;
import com.lingzhi.smart.view.section.RegionRecommendDailySection;
import com.lingzhi.smart.view.section.RegionRecommendHotSection;
import com.lingzhi.smart.view.section.RegionRecommendTypesSection;
import com.lingzhi.smart.view.sectioned.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

import static com.google.common.base.Preconditions.checkNotNull;

;

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
    @BindView(R.id.btn_setting)
    Button btnSetting;
    private boolean mIsRefreshing = false;

    private List<RecommendBean> listernBeans = new ArrayList<>();
    private List<RecommendBean> recommendBeans = new ArrayList<>();
    private List<Banner.BannerEntity> bannerEntities = new ArrayList<>();
    ResourceGroup<IconLink> recommends = new ResourceGroup<IconLink>();
    private DatedLinkGroup categorys = new DatedLinkGroup();
    ResourceGroup<Song> requisite = new ResourceGroup<Song>();
    private SectionedRecyclerViewAdapter mSectionedRecyclerViewAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.subscribe();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mSectionedRecyclerViewAdapter.removeAllSections();
        mSectionedRecyclerViewAdapter.notifyDataSetChanged();
        if (mPresenter != null) {
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
    public void onClick(View view) {
        if (view.getId() == R.id.rl_search) {
            startActivity(new Intent(getActivity(), SearchActivity.class));
        }
    }

    protected void initRecyclerView() {
        mRefreshLayout.setEnabled(false);
        mSectionedRecyclerViewAdapter = new SectionedRecyclerViewAdapter();

        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mSectionedRecyclerViewAdapter.getSectionItemViewType(position) == SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER
                        || "Listen".equals(mSectionedRecyclerViewAdapter.getTagByPosition(position))) {
                    return 3;
                } else {
                    return 1;
                }
            }
        });

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mSectionedRecyclerViewAdapter);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void banner(List<Banner.BannerEntity> bannerEntities) {
        this.bannerEntities = bannerEntities;

    }

    @Override
    public void category(DatedLinkGroup groud) {
        categorys = groud;
    }

    @Override
    public void requisite(ResourceGroup<Song> requisite) {
        this.requisite = requisite;
    }

    @Override
    public void recommend(ResourceGroup<IconLink> recommends) {
        this.recommends = recommends;
    }


    @Override
    public void finishTask() {
        mSectionedRecyclerViewAdapter.addSection("banner", new RegionRecommendBannerSection(bannerEntities));
        //分类
        mSectionedRecyclerViewAdapter.addSection("hot", new RegionRecommendTypesSection(getActivity(), categorys));
        // 每日必听
        mSectionedRecyclerViewAdapter.addSection("Listen", new RegionRecommendHotSection(getContext(), requisite));
        mSectionedRecyclerViewAdapter.addSection("Recommend", new RegionRecommendDailySection(getContext(), recommends));
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
        if (categorys != null) {
            IconLink[] categorysLinks = categorys.getLinks();
            IconLink categoryLink = categorysLinks[type];
            if (categoryLink == null) {
                Log.e(TAG, "performMainType category link is null");
                return;
            }

            Log.d(TAG, "performMainType navigator to playlist:" + categoryLink.toString());
            Navigator.navigateToMusicPlayList(getContext(), categoryLink.getOid(), categoryLink.getName(), categoryLink.getIcon(), true);
        } else {
            Log.e(TAG, "performMainType category is null");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.btn_setting)
    public void setting() {
    }
}
