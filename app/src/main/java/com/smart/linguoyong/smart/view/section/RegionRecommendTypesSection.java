package com.smart.linguoyong.smart.view.section;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.smart.linguoyong.smart.R;
import com.smart.linguoyong.smart.base.RxBus;
import com.smart.linguoyong.smart.view.sectioned.RegionRecommendTypesAdapter;
import com.smart.linguoyong.smart.view.sectioned.StatelessSection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hcc on 2016/10/21 21:15
 * 100332338@qq.com
 * <p>
 * 分区推荐分类section
 */

public class RegionRecommendTypesSection extends StatelessSection {
    private Context mContext;
    private int rid;

    //番剧类型Icons
    private int[] bangumiIcons = new int[]{
            R.drawable.ic_category_t33, R.drawable.ic_category_t32,
            R.drawable.ic_category_t153, R.drawable.ic_category_t51
    };
    //番剧类型titles
    private String[] bangumiTitles = new String[]{"连载动画", "完结动画", "国产动画", "资讯", "官方延伸"};

    public RegionRecommendTypesSection(Context context, int rid) {
        super(R.layout.layout_region_recommend_types, R.layout.layout_home_recommend_empty);
        this.mContext = context;
        this.rid = rid;
    }


    @Override
    public int getContentItemsTotal() {
        return 1;
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new RegionRecommendTypesSection.EmptyViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new RegionRecommendTypesSection.TypesViewHolder(view);
    }


    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        TypesViewHolder typesViewHolder = (TypesViewHolder) holder;
        typesViewHolder.mRecyclerView.setHasFixedSize(false);
        typesViewHolder.mRecyclerView.setNestedScrollingEnabled(false);
        setRecyclerAdapter(typesViewHolder);
    }


    private void setRecyclerAdapter(TypesViewHolder typesViewHolder) {
        RegionRecommendTypesAdapter mAdapter = null;
        switch (rid) {
            case 0:
                //番剧
                typesViewHolder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                mAdapter = new RegionRecommendTypesAdapter(typesViewHolder.mRecyclerView, bangumiIcons, bangumiTitles);
                break;
        }
        typesViewHolder.mRecyclerView.setAdapter(mAdapter);
        assert mAdapter != null;
        mAdapter.setOnItemClickListener((position, holder) -> RxBus.getInstance().post(position));
    }


    static class TypesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.types_recycler)
        RecyclerView mRecyclerView;

        TypesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class EmptyViewHolder extends RecyclerView.ViewHolder {
        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
