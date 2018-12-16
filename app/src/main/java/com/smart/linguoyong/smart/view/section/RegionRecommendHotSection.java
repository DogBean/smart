package com.smart.linguoyong.smart.view.section;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smart.linguoyong.data.source.RecommendBean;
import com.smart.linguoyong.smart.R;
import com.smart.linguoyong.smart.view.sectioned.StatelessSection;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hcc on 2016/10/21 23:28
 * 100332338@qq.com
 * <p>
 * 分区推荐热门推荐section
 */

public class RegionRecommendHotSection extends StatelessSection {
    private Context mContext;
    private int rid;
    private List<RecommendBean> recommends;

    public RegionRecommendHotSection(Context context, int rid, List<RecommendBean> recommends) {
        super(R.layout.layout_region_recommend_hot_head, R.layout.layout_region_recommend_card_item);
        this.rid = rid;
        this.recommends = recommends;
        this.mContext = context;
    }


    @Override
    public int getContentItemsTotal() {
        return recommends.size();
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        RecommendBean recommendBean = recommends.get(position);

        Glide.with(mContext)
                .load(recommendBean.getCover())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(itemViewHolder.mImage);

        itemViewHolder.mTitle.setText(recommendBean.getTitle());
        itemViewHolder.mPlay.setText(NumberUtil.converString(recommendBean.getPlay()));
        itemViewHolder.mReview.setText(NumberUtil.converString(recommendBean.getDanmaku()));
        itemViewHolder.mCardView.setOnClickListener(
                v -> VideoDetailsActivity.launch((Activity) mContext,
                        Integer.valueOf(recommendBean.getParam()), recommendBean.getCover()));
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeadViewHolder(view);
    }


    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeadViewHolder headViewHolder = (HeadViewHolder) holder;
//        if (rid == ConstantUtil.ADVERTISING_RID) {
//            headViewHolder.mRankBtn.setVisibility(View.GONE);
//        } else {
//            headViewHolder.mRankBtn.setOnClickListener(v -> startRankActivityById());
//        }
    }


    private void startRankActivityById() {

    }


    static class HeadViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_type_more)
        TextView mGetMore;

        HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
//
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view)
        CardView mCardView;
        @BindView(R.id.item_img)
        ImageView mImage;
        @BindView(R.id.item_title)
        TextView mTitle;
        @BindView(R.id.item_play)
        TextView mPlay;
        @BindView(R.id.item_review)
        TextView mReview;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
