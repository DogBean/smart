package com.smart.linguoyong.smart.view.section;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smart.linguoyong.data.source.RecommendBean;
import com.smart.linguoyong.smart.R;
import com.smart.linguoyong.smart.app.SmartApplication;
import com.smart.linguoyong.smart.loader.GlideImageLoader;
import com.smart.linguoyong.smart.view.sectioned.StatelessSection;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


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
        new GlideImageLoader().displayImage(mContext, recommendBean.cover(), itemViewHolder.mImageCover);

        itemViewHolder.mRecycleView.setAdapter(new SingleAdapter(recommendBean.getRecommendList()));
        itemViewHolder.mRecycleView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
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
        //todo 获取更多的点击事件 ,

    }

    static class SingleItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.single_item_type_title)
        TextView mTitle;

        @BindView(R.id.single_item_type_time)
        TextView mPlayTime;

        public SingleItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public class SingleAdapter extends RecyclerView.Adapter<SingleItemHolder> {

        public SingleAdapter(List<RecommendBean.PlayBean> playBeans) {
            this.playBeans = playBeans;
        }

        private List<RecommendBean.PlayBean> playBeans;

        @NonNull
        @Override
        public SingleItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_single_item, null);
            return new SingleItemHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull SingleItemHolder holder, int position) {
            if (playBeans == null || playBeans.isEmpty()) {
                Log.e(SmartApplication.TAG, "playBeans is null or empty");
                return;
            }

            RecommendBean.PlayBean playBean = playBeans.get(position);
            holder.mPlayTime.setText(String.valueOf(playBean.playtime));
            holder.mTitle.setText(playBean.title);
        }

        @Override
        public int getItemCount() {
            return playBeans == null ? 0 : playBeans.size();
        }
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
        @BindView(R.id.item_type_cover)
        ImageView mImageCover;

        @BindView(R.id.item_type_recycle)
        RecyclerView mRecycleView;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
