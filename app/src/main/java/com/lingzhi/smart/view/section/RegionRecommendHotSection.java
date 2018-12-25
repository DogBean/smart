package com.lingzhi.smart.view.section;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingzhi.smart.data.bean.Resource;
import com.lingzhi.smart.data.bean.ResourceGroup;
import com.lingzhi.smart.data.bean.ResourceList;
import com.lingzhi.smart.data.bean.Song;
import com.lingzhi.smart.data.source.RecommendBean;
import com.lingzhi.smart.R;
import com.lingzhi.smart.app.SmartApplication;
import com.lingzhi.smart.loader.GlideImageLoader;
import com.lingzhi.smart.utils.Injection;
import com.lingzhi.smart.utils.Navigator;
import com.lingzhi.smart.utils.Utils;
import com.lingzhi.smart.view.sectioned.StatelessSection;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class RegionRecommendHotSection extends StatelessSection {
    private static final String TAG = SmartApplication.TAG;
    private Context mContext;
    Song[] songs;

    public RegionRecommendHotSection(Context context, ResourceGroup<Song> requisite) {
        super(R.layout.layout_region_recommend_head, R.layout.layout_region_recommend_hot_item);
        this.songs =  requisite.getChildren();
        this.mContext = context;
    }


    @Override
    public int getContentItemsTotal() {
        return 1;
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.mRecycleView.setAdapter(new SingleAdapter(songs));
        itemViewHolder.mPlayAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: -------------------------");
            }
        });
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
        Injection.provideMainRepository().getRequisite().subscribe(new Observer<ResourceGroup>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResourceGroup resourceGroup) {
                if (resourceGroup != null) {
                    Navigator.navigateRequisite(mContext);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    static class SingleItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.single_item_type_title)
        TextView mTitle;

        @BindView(R.id.single_item_type_name)
        TextView mName;

        @BindView(R.id.single_item_type_time)
        TextView mPlayTime;

        public SingleItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public class SingleAdapter extends RecyclerView.Adapter<SingleItemHolder> {

        public SingleAdapter(Song[] songs) {
            this.songs = songs;
        }

        private Song[] songs;

        @NonNull
        @Override
        public SingleItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_single_item, null);
            return new SingleItemHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull SingleItemHolder holder, int position) {
            if (songs == null || songs.length == 0) {
                Log.e(SmartApplication.TAG, "playBeans is null or empty");
                return;
            }

            Song resource = songs[position];
            holder.mPlayTime.setText(Utils.formatSeconds(resource.getDuration()));
            holder.mTitle.setText(resource.getOname());
            holder.mName.setText(resource.getName());

        }

        @Override
        public int getItemCount() {
            return songs == null ? 0 : 3;
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

        @BindView(R.id.item_type_play_all)
        LinearLayout mPlayAll;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
