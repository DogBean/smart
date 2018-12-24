package com.lingzhi.smart.module.playList;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.lingzhi.smart.R;
import com.lingzhi.smart.data.bean.MusicInfo;
import com.lingzhi.smart.utils.CommonUtils;
import com.lingzhi.smart.utils.NetworkUtils;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayListActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {
    private static final String TAG = "PlayListActivity";

    @BindView(R.id.recyclerview)
    ObservableRecyclerView recyclerView;
    @BindView(R.id.empty)
    ImageView empty;
    @BindView(R.id.recyclerview_container)
    RelativeLayout recyclerviewContainer;
    @BindView(R.id.album_art)
    ImageView albumArt;
    @BindView(R.id.overlay)
    View overlay;
    @BindView(R.id.playlist_art)
    ImageView albumArtSmall;
    @BindView(R.id.playlist_listen_count)
    TextView playlistListenCount;
    @BindView(R.id.fra)
    FrameLayout fra;
    @BindView(R.id.album_title)
    TextView playlistTitleView;
    @BindView(R.id.album_details)
    TextView albumDetails;
    @BindView(R.id.headerdetail)
    RelativeLayout headerDetail;
    @BindView(R.id.headerview)
    FrameLayout headerViewContent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.try_again)
    TextView tryAgain;
    @BindView(R.id.state_container)
    FrameLayout stateContainer;
    @BindView(R.id.bottom_container)
    FrameLayout bottomContainer;

    private String playlsitId;
    private String albumPath, playlistName, playlistDetail, playlistCount;
    private int mActionBarSize;
    private int mStatusSize;
    private ActionBar actionBar;
    private ArrayList adapterList = new ArrayList<>();
    private PlaylistDetailAdapter mAdapter;
    private View loadView;
    private int mFlexibleSpaceImageHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_play_list);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            playlsitId = getIntent().getStringExtra("playlistid");
            albumPath = getIntent().getStringExtra("albumart");
            playlistName = getIntent().getStringExtra("playlistname");
            playlistDetail = getIntent().getStringExtra("playlistDetail");
            playlistCount = getIntent().getStringExtra("playlistcount");

        }

        mActionBarSize = CommonUtils.getActionBarHeight(this);
        mStatusSize = CommonUtils.getStatusHeight(this);
        tryAgain = (TextView) findViewById(R.id.try_again);
        setUpEverything();
    }

    private void setUpEverything() {
        setupToolbar();
        setHeaderView();
        setAlbumart();
        setList();
        loadAllLists();
    }

    private void setHeaderView() {

    }

    private void loadAllLists() {
        if (NetworkUtils.isConnected()) {
            tryAgain.setVisibility(View.GONE);
            loadView = LayoutInflater.from(this).inflate(R.layout.loading, stateContainer, false);
            stateContainer.addView(loadView);

            //todo 加载数据
//            mLoadNetList = new LoadNetPlaylistInfo();
//            mLoadNetList.execute();

        } else {
            tryAgain.setVisibility(View.VISIBLE);

        }
    }

    private void setList() {
        recyclerView.setScrollViewCallbacks(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mAdapter = new PlaylistDetailAdapter(this, adapterList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void setAlbumart() {
        if (!TextUtils.isEmpty(playlistName)) {
            playlistTitleView.setText(playlistName);
        }

        if (albumPath == null) {
            albumArtSmall.setImageResource(R.drawable.ic_placeholder_disk_210);
        } else {

            RoundedCorners roundedCorners = new RoundedCorners(20);

            RequestOptions options = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(RequestOptions.bitmapTransform(roundedCorners))
                    .placeholder(R.drawable.bili_default_image_tv)
                    .dontAnimate();

            Glide.with(this)
                    .load(albumPath)
                    .apply(options)
                    .into(albumArtSmall);

        }

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.actionbar_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("歌单");
        toolbar.setPadding(0, mStatusSize, 0, 0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        updateViews(scrollY, false);

        if (scrollY > 0 && scrollY < mFlexibleSpaceImageHeight - mActionBarSize - mStatusSize) {
            toolbar.setTitle(playlistName);
            toolbar.setSubtitle(playlistDetail);
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.toolbar_background));
        }
        if (scrollY == 0) {
            toolbar.setTitle("歌单");
            actionBar.setBackgroundDrawable(null);
        }
        if (scrollY > mFlexibleSpaceImageHeight - mActionBarSize - mStatusSize) {

//            if(mBlurDrawable != null){
//                mBlurDrawable.setColorFilter(Color.parseColor("#79000000"), PorterDuff.Mode.SRC_OVER);
//                actionBar.setBackgroundDrawable(mBlurDrawable);
//            }
        }

        float a = (float) scrollY / (mFlexibleSpaceImageHeight - mActionBarSize - mStatusSize);
        headerDetail.setAlpha(1f - a);
    }

    protected void updateViews(int scrollY, boolean animated) {
        // Translate header
        ViewHelper.setTranslationY(headerViewContent, getHeaderTranslationY(scrollY));
    }

    protected float getHeaderTranslationY(int scrollY) {
        final int headerHeight = headerViewContent.getHeight();
        int headerTranslationY = mActionBarSize + mStatusSize - headerHeight;
        if (mActionBarSize + mStatusSize <= -scrollY + headerHeight) {
            headerTranslationY = -scrollY;
        }
        return headerTranslationY;
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    class PlaylistDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        final static int FIRST_ITEM = 0;
        final static int ITEM = 1;
        private ArrayList<MusicInfo> arraylist;
        private Activity mContext;

        public PlaylistDetailAdapter(Activity context, ArrayList<MusicInfo> mList) {
            this.arraylist = mList;
            this.mContext = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            if (viewType == FIRST_ITEM) {
                return new CommonItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.header_common_item, viewGroup, false));
            } else {
                return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_playlist_detail_item, viewGroup, false));
            }
        }

        //判断布局类型
        @Override
        public int getItemViewType(int position) {
            return position == FIRST_ITEM ? FIRST_ITEM : ITEM;

        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder itemHolder, final int i) {
            if (itemHolder instanceof ItemViewHolder) {
                final MusicInfo localItem = arraylist.get(i - 1);

                //判断该条目音乐是否在播放
                if (true) {
                    ((ItemViewHolder) itemHolder).trackNumber.setVisibility(View.GONE);
                    ((ItemViewHolder) itemHolder).playState.setVisibility(View.VISIBLE);
                    ((ItemViewHolder) itemHolder).playState.setImageResource(R.drawable.ic_playing);
                } else {
                    ((ItemViewHolder) itemHolder).playState.setVisibility(View.GONE);
                    ((ItemViewHolder) itemHolder).trackNumber.setVisibility(View.VISIBLE);
                    ((ItemViewHolder) itemHolder).trackNumber.setText(i + "");
                }

                ((ItemViewHolder) itemHolder).title.setText(localItem.musicName);

            } else if (itemHolder instanceof CommonItemViewHolder) {

                ((CommonItemViewHolder) itemHolder).textView.setText("(共" + arraylist.size() + "首)");

                ((CommonItemViewHolder) itemHolder).select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            }

        }

        @Override
        public int getItemCount() {
            return arraylist == null ? 0 : arraylist.size() + 1;
        }

        public void updateDataSet(ArrayList<MusicInfo> arraylist) {
            this.arraylist = arraylist;
            this.notifyDataSetChanged();
        }

        public class CommonItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView textView;
            ImageView select;
            RelativeLayout layout;

            CommonItemViewHolder(View view) {
                super(view);
                this.textView = (TextView) view.findViewById(R.id.play_all_number);
                this.layout = (RelativeLayout) view.findViewById(R.id.play_all_layout);
                layout.setOnClickListener(this);
            }

            public void onClick(View v) {
                //// TODO: 2016/1/20
                Log.e(TAG, "播放所有歌曲");

//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        HashMap<Long, MusicInfo> infos = new HashMap<Long, MusicInfo>();
//                        int len = arraylist.size();
//                        long[] list = new long[len];
//                        for (int i = 0; i < len; i++) {
//                            MusicInfo info = arraylist.get(i);
//                            list[i] = info.songId;
//                            infos.put(list[i], info);
//                        }
//                        if (getAdapterPosition() > -1)
//                            MusicPlayer.playAll(infos, list, 0, false);
//                    }
//                },70);

            }

        }

        public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            protected TextView title, trackNumber;
            ImageView playState;

            public ItemViewHolder(View view) {
                super(view);
                this.title = (TextView) view.findViewById(R.id.song_title);
                this.trackNumber = (TextView) view.findViewById(R.id.trackNumber);
                this.playState = (ImageView) view.findViewById(R.id.play_state);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Log.e(TAG, "播放按钮被点击");

//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        HashMap<Long, MusicInfo> infos = new HashMap<Long, MusicInfo>();
//                        int len = arraylist.size();
//                        long[] list = new long[len];
//                        for (int i = 0; i < len; i++) {
//                            MusicInfo info = arraylist.get(i);
//                            list[i] = info.songId;
//                            infos.put(list[i], info);
//                        }
//                        if (getAdapterPosition() > 0)
//                            MusicPlayer.playAll(infos, list, getAdapterPosition() - 1, false);
//                    }
//                }, 70);
            }

        }
    }
}
