package com.lingzhi.smart.view.section;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.lingzhi.smart.R;
import com.lingzhi.smart.data.bean.Resource;
import com.lingzhi.smart.data.bean.ResourceList;
import com.lingzhi.smart.utils.Utils;
import com.lingzhi.smart.view.sectioned.StatelessSection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description:
 * @Author Guoyong.Lin
 * @Time 2018/12/24
 */
public class RequisiteSession extends StatelessSection {
    private Context mContext;
    private final Resource[] resources;

    public RequisiteSession(Context context, ResourceList resourceList) {
        super(R.layout.layout_region_requisite_head, R.layout.layout_region_requisite_item);
        resources = resourceList.getChildren();
        this.mContext = context;
    }

    @Override
    public int getContentItemsTotal() {
        return resources.length;
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
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        Resource resource = resources[position];
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        RoundedCorners roundedCorners = new RoundedCorners(10);

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.bitmapTransform(roundedCorners))
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate();


        Glide.with(mContext)
                .load(resource.getIcon())
                .apply(options)
                .into(itemViewHolder.imageCover);

        itemViewHolder.singleItemTypeTitle.setText(resource.getOname());
        itemViewHolder.singleItemTypeName.setText(resource.getName());
        itemViewHolder.tvPlayTime.setText(Utils.formatSeconds(resource.getDuration()));
    }

    static class HeadViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_type_title)
        TextView itemTypeTitle;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.single_item_type_title)
        TextView singleItemTypeTitle;
        @BindView(R.id.single_item_type_name)
        TextView singleItemTypeName;
        @BindView(R.id.iv_play)
        ImageView ivPlay;
        @BindView(R.id.tv_play_num)
        TextView tvPlayNum;
        @BindView(R.id.iv_content)
        ImageView ivContent;
        @BindView(R.id.tv_play_time)
        TextView tvPlayTime;

        @BindView(R.id.item_type_cover)
        ImageView imageCover;

        ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
