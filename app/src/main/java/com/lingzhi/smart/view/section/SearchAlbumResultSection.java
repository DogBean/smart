package com.lingzhi.smart.view.section;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingzhi.smart.R;
import com.lingzhi.smart.app.SmartApplication;
import com.lingzhi.smart.base.RxBus;
import com.lingzhi.smart.data.bean.AlbumBean;
import com.lingzhi.smart.data.bean.SearchResultBean;
import com.lingzhi.smart.event.AlbumTagEvent;
import com.lingzhi.smart.loader.GlideImageLoader;
import com.lingzhi.smart.view.sectioned.StatelessSection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hcc on 2016/10/21 21:15
 * 100332338@qq.com
 * <p>
 * 分区推荐分类section
 */

public class SearchAlbumResultSection extends StatelessSection {
    private static final String TAG = SmartApplication.TAG;


    private int rid;
    private Context context;
    private SearchResultBean dailyBeans;


    public SearchAlbumResultSection(Context context, int rid, SearchResultBean dailyBeans) {
        super(R.layout.layout_album_search_head, R.layout.item_search_album);
        this.rid = rid;
        this.dailyBeans = dailyBeans;
        this.context = context;
    }


    @Override
    public int getContentItemsTotal() {
        if (dailyBeans.getAlbumBeanList() == null) {
            return 0;
        } else if (dailyBeans.getAlbumBeanList().size() > 3) {
            return 3;
        } else {
            return dailyBeans.getAlbumBeanList().size();
        }
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeadViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeadViewHolder itemViewHolder = (HeadViewHolder) holder;
        itemViewHolder.itemTypeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxBus.getInstance().post(new AlbumTagEvent());
            }
        });
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        AlbumBean bean = dailyBeans.getAlbumBeanList().get(position);
        new GlideImageLoader().displayImage(context, bean.getImageUrl(), itemViewHolder.mulitImageSearchAlbum);
        itemViewHolder.tvNums.setText(String.valueOf(bean.getContentNum()));
        itemViewHolder.mulitTvSearchAlbum.setText(bean.getName());
    }


    static class HeadViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_type_title)
        TextView itemTypeTitle;
        @BindView(R.id.item_type_more)
        TextView itemTypeMore;

        HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mulit_image_search_album)
        ImageView mulitImageSearchAlbum;
        @BindView(R.id.mulit_tv_search_album)
        TextView mulitTvSearchAlbum;
        @BindView(R.id.mulit_tv_search_go)
        LinearLayout mulitTvSearchGo;
        @BindView(R.id.item_nums)
        TextView tvNums;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
