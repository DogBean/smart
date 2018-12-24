package com.lingzhi.smart.view.sectioned;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lingzhi.smart.R;
import com.lingzhi.smart.data.bean.DatedLinkGroup;
import com.lingzhi.smart.data.bean.IconLink;
import com.lingzhi.smart.loader.GlideImageLoader;
import com.lingzhi.smart.view.AbsRecyclerViewAdapter;


/**
 * Created by hcc on 2016/10/21 21:55
 * 100332338@qq.com
 * <p>
 * 分区推荐页面类型分类Icons的adapter
 */

public class RegionRecommendTypesAdapter extends AbsRecyclerViewAdapter {
    private DatedLinkGroup mData;
    private IconLink[] links;

    public RegionRecommendTypesAdapter(RecyclerView recyclerView, DatedLinkGroup data) {
        super(recyclerView);
        this.mData = data;
        links = data.getLinks();
    }


    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(
                LayoutInflater.from(getContext()).inflate(R.layout.item_types_icon, parent, false));
    }


    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            new GlideImageLoader().displayImage(getContext(), links[position].getIcon(), itemViewHolder.mIcon);

            itemViewHolder.mTitle.setText(links[position].getName());
        }
        super.onBindViewHolder(holder, position);
    }


    @Override
    public int getItemCount() {
        return links.length;
    }


    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        ImageView mIcon;
        TextView mTitle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mIcon = $(R.id.item_icon);
            mTitle = $(R.id.item_title);
        }
    }
}
