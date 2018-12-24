package com.lingzhi.smart.view.section;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lingzhi.smart.R;
import com.lingzhi.smart.app.SmartApplication;
import com.lingzhi.smart.base.RxBus;
import com.lingzhi.smart.data.bean.AudioBean;
import com.lingzhi.smart.data.bean.SearchResultBean;
import com.lingzhi.smart.event.AudioTagEvent;
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

public class SearchAudioResultSection extends StatelessSection {
    private static final String TAG = SmartApplication.TAG;

    private int rid;
    private Context context;
    private SearchResultBean dailyBeans;


    public SearchAudioResultSection(Context context, int rid, SearchResultBean dailyBeans) {
        super(R.layout.item_search_head_audio, R.layout.item_search_audio);
        this.rid = rid;
        this.dailyBeans = dailyBeans;
        this.context = context;
    }


    @Override
    public int getContentItemsTotal() {
        if (dailyBeans.getAudioBeanList() == null) {
            return 0;
        } else if (dailyBeans.getAudioBeanList().size() > 3) {
            return 3;
        } else {
            return dailyBeans.getAudioBeanList().size();
        }
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeadViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeadViewHolder headViewHolder = (HeadViewHolder) holder;
        headViewHolder.itemTypeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxBus.getInstance().post(new AudioTagEvent());
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        AudioBean bean = dailyBeans.getAudioBeanList().get(position);
        new GlideImageLoader().displayImage(context, bean.getImageUrl(), itemViewHolder.item_img);
        itemViewHolder.tv_content_num.setText(String.valueOf(bean.getPlayNum()));
        itemViewHolder.tv_play_num.setText(bean.getTime());
        itemViewHolder.tv_name.setText(bean.getName());

    }

    static class HeadViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_type_title)
        TextView itemTypeTitle;
        @BindView(R.id.item_type_more)
        TextView itemTypeMore;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_img)
        ImageView item_img;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_content_num)
        TextView tv_content_num;
        @BindView(R.id.tv_play_num)
        TextView tv_play_num;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
