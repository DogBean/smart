package com.smart.linguoyong.smart.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.smart.linguoyong.smart.R;
import com.smart.linguoyong.smart.base.RxLazyFragment;
import com.smart.linguoyong.data.source.Banner;
import com.smart.linguoyong.smart.view.banner.RegionRecommendBannerSection;
import com.smart.linguoyong.smart.view.section.RegionRecommendTypesSection;
import com.smart.linguoyong.smart.view.sectioned.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Guoyong.Lin on 2018/12/14
 * 首页模块
 **/
public class MainFragment extends RxLazyFragment implements MainContract.View {

    @BindView(R.id.main_recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.main_swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private MainContract.Presenter mPresenter;

    private List<Banner.BannerEntity> bannerEntities = new ArrayList<>();

    private SectionedRecyclerViewAdapter mSectionedRecyclerViewAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mSectionedRecyclerViewAdapter.removeAllSections();
        mSectionedRecyclerViewAdapter.notifyDataSetChanged();
        mPresenter.unsubscribe();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = root.findViewById(R.id.main_recycle);
        ButterKnife.bind(this, root);
        initRecyclerView();
        return root;
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
        mSectionedRecyclerViewAdapter.addSection(new RegionRecommendTypesSection(getActivity(), 0) );
        mSectionedRecyclerViewAdapter.notifyDataSetChanged();
    }
}
