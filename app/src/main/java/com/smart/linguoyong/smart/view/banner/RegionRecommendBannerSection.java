package com.smart.linguoyong.smart.view.banner;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.smart.linguoyong.data.source.Banner;
import com.smart.linguoyong.smart.R;
import com.smart.linguoyong.smart.view.sectioned.StatelessSection;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hcc on 16/8/27 14:06
 * 100332338@qq.com
 * <p/>
 * 分区推荐页面轮播图Section
 */
public class RegionRecommendBannerSection extends StatelessSection {
    private List<Banner.BannerEntity> banners;

    public RegionRecommendBannerSection(List<Banner.BannerEntity> banners) {
        super(R.layout.layout_banner, R.layout.layout_home_recommend_empty);
        this.banners = banners;
    }


    @Override
    public int getContentItemsTotal() {
        return 1;
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new EmptyViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new BannerViewHolder(view);
    }


    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
        bannerViewHolder.mBannerView.delayTime(5).build(banners);
    }


    static class EmptyViewHolder extends RecyclerView.ViewHolder {
        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.home_recommended_banner)
        BannerView mBannerView;

        BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mBannerView = itemView.findViewById(R.id.home_recommended_banner);
        }
    }
}
