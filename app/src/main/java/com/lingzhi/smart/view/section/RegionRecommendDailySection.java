package com.lingzhi.smart.view.section;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lingzhi.smart.data.bean.IconLink;
import com.lingzhi.smart.data.bean.Resource;
import com.lingzhi.smart.data.bean.ResourceGroup;
import com.lingzhi.smart.data.bean.ResourceList;
import com.lingzhi.smart.data.source.MainRepository;
import com.lingzhi.smart.data.source.RecommendBean;
import com.lingzhi.smart.data.source.RecommendDailyBean;
import com.lingzhi.smart.R;
import com.lingzhi.smart.app.SmartApplication;
import com.lingzhi.smart.data.source.remote.Resp;
import com.lingzhi.smart.loader.GlideImageLoader;
import com.lingzhi.smart.utils.Injection;
import com.lingzhi.smart.utils.Navigator;
import com.lingzhi.smart.view.sectioned.StatelessSection;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RegionRecommendDailySection extends StatelessSection {
    private static final String TAG = SmartApplication.TAG;
    private Context context;
    private IconLink[] resources;


    public RegionRecommendDailySection(Context context, ResourceGroup<IconLink> resources) {
        super(R.layout.layout_region_recommend_head, R.layout.layout_region_recommend_daily_card_item);
        this.resources = resources.getChildren();
        this.context = context;
    }


    @Override
    public int getContentItemsTotal() {
        return resources.length;
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
        IconLink iconLink = resources[position];
        int albumId = iconLink.getOid();
        String albumName = iconLink.getName();
        String albumPath = iconLink.getIcon();
        new GlideImageLoader().displayImage(context, albumPath, itemViewHolder.mImageView);
        itemViewHolder.mTitle.setText(albumName);
        itemViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigator.navigateToMusicPlayList(context, albumId, albumName, albumPath, false);
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
        @BindView(R.id.item_recommend_daily_title)
        TextView mTitle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
