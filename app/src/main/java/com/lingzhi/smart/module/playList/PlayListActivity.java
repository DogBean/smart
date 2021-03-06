package com.lingzhi.smart.module.playList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.lingzhi.smart.R;
import com.lingzhi.smart.data.bean.ResourceGroup;
import com.lingzhi.smart.data.bean.Song;
import com.lingzhi.smart.data.request.AlbumRequest;
import com.lingzhi.smart.data.source.remote.ApiHelper;
import com.lingzhi.smart.data.source.remote.Resp;
import com.lingzhi.smart.module.music.QuickControlsFragment;
import com.lingzhi.smart.module.music.player.Player;
import com.lingzhi.smart.utils.CommonUtils;
import com.lingzhi.smart.utils.DisplayUtil;
import com.lingzhi.smart.utils.FastBlurUtil;
import com.lingzhi.smart.utils.Injection;
import com.lingzhi.smart.utils.NetworkUtils;
import com.lingzhi.smart.utils.Utils;
import com.nineoldandroids.view.ViewHelper;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemStateChangedListener;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class PlayListActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {
    private static final String TAG = "PlayListActivity";

    private static final String ALBUM_ID = "album_id";
    private static final String ALBUM_NAME = "album_name";
    private static final String ALBUM_PATH = "album_path";
    private static final String SUPPORT_DRAG = "support_drag";

    @BindView(R.id.recyclerview)
    SwipeMenuRecyclerView recyclerView;
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
    FrameLayout loadFrameLayout;
    @BindView(R.id.bottom_container)
    FrameLayout bottomContainer;

    private int playlsitId;
    private String albumPath, playlistName, playlistDetail, playlistCount;
    private int mActionBarSize;
    private int mStatusSize;
    private ActionBar actionBar;
    private ArrayList<Song> adapterList = new ArrayList<Song>();
    private PlaylistDetailAdapter mAdapter;
    private View loadView;
    private int mFlexibleSpaceImageHeight;
    private boolean drag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_play_list);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            playlsitId = getIntent().getIntExtra(ALBUM_ID, 0);
            albumPath = getIntent().getStringExtra(ALBUM_PATH);
            drag = getIntent().getBooleanExtra(SUPPORT_DRAG, false);
            playlistName = getIntent().getStringExtra(ALBUM_NAME);
            playlistDetail = getIntent().getStringExtra("playlistDetail");
            playlistCount = getIntent().getStringExtra("playlistcount");
        }

        mActionBarSize = CommonUtils.getActionBarHeight(this);
        mStatusSize = CommonUtils.getStatusHeight(this);
        tryAgain = (TextView) findViewById(R.id.try_again);
        setUpEverything();
        showQuickControl(true);
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
            loadView = LayoutInflater.from(this).inflate(R.layout.loading, loadFrameLayout, false);
            loadFrameLayout.addView(loadView);

            Disposable subscribe = Injection.provideMainRepository()
                    .album(playlsitId)
                    .subscribe(resourceListResp -> {
                        Song[] children = resourceListResp.getData().getChildren();
                        Collections.addAll(adapterList, children);
                        loadFrameLayout.removeAllViews();
                        recyclerView.setVisibility(View.VISIBLE);
                        mAdapter.updateDataSet(adapterList);
                    }, throwable -> Log.e(TAG, "load AllList error:" + throwable));
        } else {
            tryAgain.setVisibility(View.VISIBLE);
        }
    }

    private QuickControlsFragment fragment; //底部播放控制栏

    protected void showQuickControl(boolean show) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (show) {
            if (fragment == null) {
                fragment = QuickControlsFragment.newInstance();
                ft.add(R.id.bottom_container, fragment).commitAllowingStateLoss();
            } else {
                ft.show(fragment).commitAllowingStateLoss();
            }
        } else {
            if (fragment != null)
                ft.hide(fragment).commitAllowingStateLoss();
        }
    }

    private void setList() {
//        recyclerView.setScrollViewCallbacks(this);
        recyclerView.setLongPressDragEnabled(drag); // 长按拖拽，默认关闭。
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mAdapter = new PlaylistDetailAdapter(this, adapterList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setOnItemMoveListener(onItemMoveListener);// 监听拖拽和侧滑删除，更新UI和数据源。
        recyclerView.setOnItemStateChangedListener(mOnItemStateChangedListener); // 监听Item的手指状态，拖拽、侧
    }

    /**
     * 监听拖拽和侧滑删除，更新UI和数据源。
     */
    private OnItemMoveListener onItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
            // 不同的ViewType不能拖拽换位置。
            if (srcHolder.getItemViewType() != targetHolder.getItemViewType()) return false;

            int fromPosition = srcHolder.getAdapterPosition();
            int toPosition = targetHolder.getAdapterPosition();

            Collections.swap(adapterList, fromPosition, toPosition);
            mAdapter.notifyItemMoved(fromPosition, toPosition);

            return true;// 返回true表示处理了并可以换位置，返回false表示你没有处理并不能换位置。
        }

        @Override
        public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
            int position = srcHolder.getAdapterPosition();
            adapterList.remove(position);
            mAdapter.notifyItemRemoved(position);
            Toast.makeText(PlayListActivity.this, "现在的第" + position + "条被删除。", Toast.LENGTH_SHORT).show();
        }

    };

    @Override
    protected void onStop() {

        Song[] songs = adapterList.toArray(new Song[]{});
        ResourceGroup<Song> group = new ResourceGroup<>();
        group.setChildren(songs);
        ApiHelper.updateAlbum(new AlbumRequest(playlsitId, group)).subscribe(new Consumer<Resp>() {
            @Override
            public void accept(Resp resp) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, "update album fail:" + throwable);
            }
        });
        super.onStop();
    }

    @Override
    protected void onPause() {

        super.onPause();
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


            Glide.with(this).asBitmap()
                    .load(albumPath).listener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    Drawable drawable = getForegroundDrawable(resource);
                    albumArt.setBackground(drawable);
                    return false;
                }
            }).submit();
        }

    }

    private Drawable getForegroundDrawable(Bitmap bitmap) {
        /*得到屏幕的宽高比，以便按比例切割图片一部分*/
        final float widthHeightSize = (float) (DisplayUtil.getScreenWidth(this)
                * 1.0 / DisplayUtil.getScreenHeight(this) * 1.0);

        int cropBitmapWidth = (int) (widthHeightSize * bitmap.getHeight());
        int cropBitmapWidthX = (int) ((bitmap.getWidth() - cropBitmapWidth) / 2.0);

        /*切割部分图片*/
        Bitmap cropBitmap = Bitmap.createBitmap(bitmap, cropBitmapWidthX, 0, cropBitmapWidth,
                bitmap.getHeight());
        /*缩小图片*/
        Bitmap scaleBitmap = Bitmap.createScaledBitmap(cropBitmap, bitmap.getWidth() / 50, bitmap
                .getHeight() / 50, false);
        /*模糊化*/
        final Bitmap blurBitmap = FastBlurUtil.doBlur(scaleBitmap, 8, true);

        final Drawable foregroundDrawable = new BitmapDrawable(blurBitmap);
        /*加入灰色遮罩层，避免图片过亮影响其他控件*/
        foregroundDrawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        return foregroundDrawable;
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

    public static Intent getCallingIntent(Context context, int albumId, String albumName, String albumPath, boolean drag) {
        Intent intent = new Intent(context, PlayListActivity.class);
        intent.putExtra(ALBUM_ID, albumId);
        intent.putExtra(ALBUM_NAME, albumName);
        intent.putExtra(ALBUM_PATH, albumPath);
        intent.putExtra(SUPPORT_DRAG, drag);
        return intent;
    }

    class PlaylistDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        final static int FIRST_ITEM = 0;
        final static int ITEM = 1;
        private ArrayList<Song> arraylist;
        private Activity mContext;

        public PlaylistDetailAdapter(Activity context, ArrayList<Song> mList) {
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
                final Song localItem = arraylist.get(i - 1);
                Song playingSong = Player.getInstance().getPlayingSong();
                //判断该条目音乐是否在播放
                if (playingSong != null && TextUtils.equals(String.valueOf(playingSong.getId()), String.valueOf(localItem.getId()))) {
                    ((ItemViewHolder) itemHolder).title.setTextColor(getResources().getColor(R.color.listTextColor));
                    ((ItemViewHolder) itemHolder).playState.setVisibility(View.VISIBLE);
                    ((ItemViewHolder) itemHolder).playState.setImageResource(R.drawable.ic_playing);
                    ((ItemViewHolder) itemHolder).trackNumber.setVisibility(View.GONE);
                } else {
                    ((ItemViewHolder) itemHolder).title.setTextColor(getResources().getColor(R.color.listTextNormal));
                    ((ItemViewHolder) itemHolder).playState.setVisibility(View.GONE);
                    ((ItemViewHolder) itemHolder).trackNumber.setVisibility(View.VISIBLE);
                    ((ItemViewHolder) itemHolder).trackNumber.setText(String.valueOf(i));
                }

                //判断该条目是否在 默认分类中

                if (true) {
                    ((ItemViewHolder) itemHolder).addList.setImageResource(R.drawable.ic_add_list);
                } else {
                    ((ItemViewHolder) itemHolder).addList.setImageResource(R.drawable.ic_rm_song);
                }
                ((ItemViewHolder) itemHolder).addList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                ((ItemViewHolder) itemHolder).title.setText(localItem.getName());
                ((ItemViewHolder) itemHolder).time.setText(Utils.formatSeconds(localItem.getDuration()));
                ((ItemViewHolder) itemHolder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Player.getInstance().play(localItem);
                    }
                });
            } else if (itemHolder instanceof CommonItemViewHolder) {
                ((CommonItemViewHolder) itemHolder).textView.setText("(共" + arraylist.size() + "首)");
            }

        }

        @Override
        public int getItemCount() {
            return arraylist == null ? 0 : arraylist.size() + 1;
        }

        public void updateDataSet(ArrayList<Song> arraylist) {
            this.arraylist = arraylist;
            this.notifyDataSetChanged();
        }

        public class CommonItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView textView;
            RelativeLayout layout;

            CommonItemViewHolder(View view) {
                super(view);
                this.textView = (TextView) view.findViewById(R.id.play_all_number);
                this.layout = (RelativeLayout) view.findViewById(R.id.play_all_layout);
                layout.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                //// TODO: 2016/1/20
                Log.e(TAG, "播放所有歌曲");
                if (arraylist.size() > 1) {
                    Song song = arraylist.get(1);
                    Player.getInstance().play(song);
                }

            }

        }

        public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            protected TextView title, trackNumber, time;
            ImageView playState, addList;

            public ItemViewHolder(View view) {
                super(view);
                this.title = (TextView) view.findViewById(R.id.song_title);
                this.playState = (ImageView) view.findViewById(R.id.play_state);
                this.time = view.findViewById(R.id.song_time);
                this.trackNumber = view.findViewById(R.id.trackNumber);
                this.addList = view.findViewById(R.id.btn_add_to_list);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Log.e(TAG, "播放按钮被点击");


            }

        }
    }

    /**
     * Item的拖拽/侧滑删除时，手指状态发生变化监听。
     */
    private OnItemStateChangedListener mOnItemStateChangedListener = new OnItemStateChangedListener() {
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState == OnItemStateChangedListener.ACTION_STATE_DRAG) {

                // 拖拽的时候背景就透明了，这里我们可以添加一个特殊背景。
                viewHolder.itemView.setBackgroundColor(
                        ContextCompat.getColor(PlayListActivity.this, R.color.white_pressed));
            } else if (actionState == OnItemStateChangedListener.ACTION_STATE_SWIPE) {
            } else if (actionState == OnItemStateChangedListener.ACTION_STATE_IDLE) {

                // 在手松开的时候还原背景。
                ViewCompat.setBackground(viewHolder.itemView,
                        ContextCompat.getDrawable(PlayListActivity.this, R.drawable.select_white));
            }
        }
    };


}
