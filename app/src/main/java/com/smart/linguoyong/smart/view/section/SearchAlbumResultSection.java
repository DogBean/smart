package com.smart.linguoyong.smart.view.section;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smart.linguoyong.data.bean.AlbumBean;
import com.smart.linguoyong.data.bean.SearchResultBean;
import com.smart.linguoyong.smart.R;
import com.smart.linguoyong.smart.app.SmartApplication;
import com.smart.linguoyong.smart.base.RxBus;
import com.smart.linguoyong.smart.event.AlbumTagEvent;
import com.smart.linguoyong.smart.loader.GlideImageLoader;
import com.smart.linguoyong.smart.view.sectioned.StatelessSection;

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
        super(R.layout.item_search_zj);
        this.rid = rid;
        this.dailyBeans = dailyBeans;
        this.context = context;
    }


    @Override
    public int getContentItemsTotal() {
        if(dailyBeans.getAlbumBeanList() == null){
            return 0;
        }else if(dailyBeans.getAlbumBeanList().size() > 3){
            return 3;
        }else {
            return dailyBeans.getAlbumBeanList().size();
        }
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new SearchAlbumResultSection.ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        SearchAlbumResultSection.ItemViewHolder itemViewHolder = (SearchAlbumResultSection.ItemViewHolder) holder;
        AlbumBean bean = dailyBeans.getAlbumBeanList().get(position);
        new GlideImageLoader().displayImage(context, bean.getImageUrl(), itemViewHolder.item_img);
        itemViewHolder.tv_content_num.setText(String.valueOf(bean.getContentNum()));
        itemViewHolder.tv_play_num.setText(String.valueOf(bean.getPlayNum()));
        itemViewHolder.tv_name.setText(bean.getName());
        if(position == 2){
            itemViewHolder.ll_load_more.setVisibility(View.VISIBLE);
            itemViewHolder.tv_more.setText(context.getString(R.string.load_more_album,dailyBeans.getAlbumTotal()));
        }else {
            itemViewHolder.ll_load_more.setVisibility(View.GONE);
        }
        itemViewHolder.ll_load_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxBus.getInstance().post(new AlbumTagEvent());
            }
        });
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
        @BindView(R.id.tv_more)
        TextView tv_more;
        @BindView(R.id.ll_load_more)
        LinearLayout ll_load_more;
        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
