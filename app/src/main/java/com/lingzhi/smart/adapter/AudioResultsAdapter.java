package com.lingzhi.smart.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lingzhi.smart.data.bean.AlbumBean;
import com.lingzhi.smart.data.bean.AudioBean;
import com.lingzhi.smart.R;
import com.lingzhi.smart.view.AbsRecyclerViewAdapter;

import java.util.List;

/**
 * Created by cs
 * 专辑搜索结果
 */
public class AudioResultsAdapter extends AbsRecyclerViewAdapter {
    private List<AudioBean> albumBeans;

    public AudioResultsAdapter(RecyclerView recyclerView, List<AudioBean> albums) {
        super(recyclerView);
        this.albumBeans = albums;
    }


    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).
                inflate(R.layout.item_search_audio, parent, false));
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            AudioBean itemsBean = albumBeans.get(position);

            Glide.with(getContext())
                    .load(itemsBean.getImageUrl()).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher)).into(itemViewHolder.mImage);

            itemViewHolder.mTitle.setText(itemsBean.getName());
            itemViewHolder.mContentNum.setText(itemsBean.getTime());
            itemViewHolder.mPlayNum.setText(String.valueOf(itemsBean.getPlayNum()));
        }
        super.onBindViewHolder(holder, position);
    }


    @Override
    public int getItemCount() {
        return albumBeans.size();
    }


    public class ItemViewHolder extends ClickableViewHolder {

        ImageView mImage;
        TextView mTitle;
        TextView mContentNum;
        TextView mPlayNum;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mImage = $(R.id.item_img);
            mTitle = $(R.id.tv_name);
            mContentNum = $(R.id.tv_content_num);
            mPlayNum = $(R.id.tv_play_num);
        }
    }
}
