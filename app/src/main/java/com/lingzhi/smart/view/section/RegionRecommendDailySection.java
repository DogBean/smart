package com.lingzhi.smart.view.section;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lingzhi.smart.data.source.RecommendBean;
import com.lingzhi.smart.data.source.RecommendDailyBean;
import com.lingzhi.smart.R;
import com.lingzhi.smart.app.SmartApplication;
import com.lingzhi.smart.loader.GlideImageLoader;
import com.lingzhi.smart.view.sectioned.StatelessSection;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegionRecommendDailySection extends StatelessSection {
    private static final String TAG = SmartApplication.TAG;
    private int rid;
    private Context context;
    private List<RecommendDailyBean> dailyBeans;


    public RegionRecommendDailySection(Context context, int rid, List<RecommendDailyBean> dailyBeans) {
        super(R.layout.layout_region_recommend_head, R.layout.layout_region_recommend_daily_card_item);
        this.rid = rid;
        this.dailyBeans = dailyBeans;
        this.context = context;
    }


    @Override
    public int getContentItemsTotal() {
        return dailyBeans.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeadViewHolder(view);
    }


    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeadViewHolder headViewHolder = (HeadViewHolder) holder;
        headViewHolder.mGetMore.setOnClickListener(v -> startGetMoreActivityById());
    }

    private void startGetMoreActivityById() {
        Log.e(TAG, "startGetMoreActivityById 被点击");
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        RecommendDailyBean bean = dailyBeans.get(position);
        new GlideImageLoader().displayImage(context, bean.image, itemViewHolder.mImageView);
        itemViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "每日推荐 item 被点击");
            }
        });
    }

    static class HeadViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_type_more)
        TextView mGetMore;

        HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recommend_daily_card_view)
        CardView mCardView;

        @BindView(R.id.item_recommend_daily_img)
        ImageView mImageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
