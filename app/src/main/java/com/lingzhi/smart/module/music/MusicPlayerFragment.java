package com.lingzhi.smart.module.music;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lingzhi.smart.R;
import com.lingzhi.smart.base.RxLazyFragment;
import com.lingzhi.smart.data.bean.Song;
import com.lingzhi.smart.module.music.event.PlayListNowEvent;
import com.lingzhi.smart.module.music.event.PlaySongEvent;
import com.lingzhi.smart.module.music.model.PlayList;
import com.lingzhi.smart.module.music.player.IPlayback;
import com.lingzhi.smart.module.music.player.PlayMode;
import com.lingzhi.smart.module.music.player.PlaybackService;
import com.lingzhi.smart.module.music.player.Player;
import com.lingzhi.smart.utils.DisplayUtil;
import com.lingzhi.smart.utils.FastBlurUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @Description:
 * @Author Guoyong.Lin
 * @Time 2018/12/18
 */
public class MusicPlayerFragment extends RxLazyFragment implements MusicPlayerContract.View, IPlayback.Callback {

    private static final String TAG = "MusicPlayerFragment";
    private static final long UPDATE_PROGRESS_INTERVAL = 1000;

    @BindView(R.id.image_view_album)
    ImageView imageViewAlbum;

    @BindView(R.id.text_view_name)
    TextView textViewName;
    @BindView(R.id.text_view_progress)
    TextView textViewProgress;
    @BindView(R.id.text_view_duration)
    TextView textViewDuration;
    @BindView(R.id.seek_bar)
    SeekBar seekBarProgress;

    @BindView(R.id.button_play_mode_toggle)
    ImageView buttonPlayModeToggle;
    @BindView(R.id.button_play_toggle)
    ImageView buttonPlayToggle;
    @BindView(R.id.button_list_toggle)
    ImageView button_list_toggle;
    @BindView(R.id.rootLayout)
    RelativeLayout mRootLayout;
    Unbinder unbinder;

    private Player mPlayer;

    private Handler mHandler = new Handler();

    private MusicPlayerContract.Presenter mPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mPlayer = Player.getInstance();
        mPlayer.registerCallback(this);
        new MusicPlayerPresenter(getActivity(), this).subscribe();

        Drawable foregroundDrawable = getForegroundDrawable(R.drawable.default_record_album);
        mRootLayout.setBackground(foregroundDrawable);
    }

    private void updateProgressTextWithProgress(int progress) {
        int targetDuration = getDuration(progress);
        textViewProgress.setText(TimeUtils.formatDuration(targetDuration));
    }


    @Override
    public void onDestroyView() {
        mPlayer.unregisterCallback(this);
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    //播放
    @OnClick(R.id.button_play_toggle)
    public void onPlayToggleAction(View view) {
        Log.e(TAG, "onPlayToggleAction button_play_toggle");



    }

    // 播放模式
    @OnClick(R.id.button_play_mode_toggle)
    public void onPlayModeToggleAction(View view) {
        PlayMode current = PreferenceManager.lastPlayMode(getActivity());
        PlayMode newMode = PlayMode.switchNextMode(current);
        PreferenceManager.setPlayMode(getActivity(), newMode);
        updatePlayMode(newMode);
    }

    //上一首
    @OnClick(R.id.button_play_last)
    public void onPlayLastAction(View view) {
        Log.e(TAG, "onPlayLastAction: onPlayLastAction");
    }

    //下一首
    @OnClick(R.id.button_play_next)
    public void onPlayNextAction(View view) {
        Log.e(TAG, "onPlayNextAction button_play_next");
    }

    // 列表
    @OnClick(R.id.button_list_toggle)
    public void onOpenListAction(View view) {
        Log.e(TAG, "onOpenListAction button_list_toggle");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void updateProgressTextWithDuration(int duration) {
        textViewProgress.setText(TimeUtils.formatDuration(duration));
    }

    private int getDuration(int progress) {
        return (int) (getCurrentSongDuration() * ((float) progress / seekBarProgress.getMax()));
    }

    private int getCurrentSongDuration() {
        Song currentSong = mPlayer.getPlayingSong();
        int duration = 0;
        if (currentSong != null) {
            duration = currentSong.getDuration();
        }
        return duration;
    }


    @Override
    protected Subscription subscribeEvents() {
        return RxBus.getInstance().toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o instanceof PlaySongEvent) {
                            onPlaySongEvent((PlaySongEvent) o);
                        } else if (o instanceof PlayListNowEvent) {
                            onPlayListNowEvent((PlayListNowEvent) o);
                        }
                    }
                })
                .subscribe(RxBus.defaultSubscriber());
    }

    private void onPlaySongEvent(PlaySongEvent event) {
        Song song = event.song;
        playSong(song);
    }

    private void onPlayListNowEvent(PlayListNowEvent event) {
        PlayList playList = event.playList;
        int playIndex = event.playIndex;
        playSong(playList, playIndex);
    }

    private void playSong(Song song) {
        PlayList playList = new PlayList(song);
        playSong(playList, 0);
    }

    private void playSong(PlayList playList, int playIndex) {
        if (playList == null) return;

        playList.setPlayMode(PreferenceManager.lastPlayMode(getActivity()));
        // boolean result =
        Song song = playList.getCurrentSong();
        onSongUpdated(song);

    }

    @Override
    public void handleError(Throwable error) {

    }

    @Override
    public void onPlaybackServiceBound(PlaybackService service) {

    }

    @Override
    public void onPlaybackServiceUnbound() {

    }

    @Override
    public void onSongUpdated(@Nullable Song song) {
        if (song == null) {
            buttonPlayToggle.setImageResource(R.drawable.ic_play_music);
            seekBarProgress.setProgress(0);
            updateProgressTextWithProgress(0);
            return;
        }

        // Step 1: Song name and artist
        textViewName.setText(song.getDisplayName());

        textViewDuration.setText(TimeUtils.formatDuration(song.getDuration()));
        // Step 4: Keep these things updated
        // - Album rotation
        // - Progress(textViewProgress & seekBarProgress)
        Bitmap bitmap = AlbumUtils.parseAlbum(song);
        if (bitmap == null) {
            imageViewAlbum.setImageResource(R.drawable.default_record_album);
        } else {
            imageViewAlbum.setImageBitmap(AlbumUtils.getCroppedBitmap(bitmap));
        }

        buttonPlayToggle.setImageResource(R.drawable.ic_pause_music);
    }

    @Override
    public void updatePlayMode(PlayMode playMode) {
        if (playMode == null) {
            playMode = PlayMode.getDefault();
        }
        switch (playMode) {
            case LIST:
                buttonPlayModeToggle.setImageResource(R.drawable.ic_music_play_mode_recycle);
                break;
            case LOOP:
                buttonPlayModeToggle.setImageResource(R.drawable.ic_music_list);
                break;
            case SHUFFLE:
                buttonPlayModeToggle.setImageResource(R.drawable.ic_music_play_random);
                break;
            case SINGLE:
                buttonPlayModeToggle.setImageResource(R.drawable.ic_music_play_mode_single);
                break;
        }
    }

    @Override
    public void updatePlayToggle(boolean play) {
        buttonPlayToggle.setImageResource(play ? R.drawable.ic_pause_music : R.drawable.ic_play_music);
    }

    @Override
    public void setPresenter(MusicPlayerContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onSwitchLast(@Nullable Song last) {
        onSongUpdated(last);
    }

    @Override
    public void onSwitchNext(@Nullable Song next) {
        onSongUpdated(next);
    }

    @Override
    public void onComplete(@Nullable Song next) {
        onSongUpdated(next);
    }

    @Override
    public void onPlayStatusChanged(boolean isPlaying) {
        updatePlayToggle(isPlaying);
    }

    private Drawable getForegroundDrawable(int musicPicRes) {
        /*得到屏幕的宽高比，以便按比例切割图片一部分*/
        final float widthHeightSize = (float) (DisplayUtil.getScreenWidth(getContext())
                * 1.0 / DisplayUtil.getScreenHeight(getContext()) * 1.0);

        Bitmap bitmap = getForegroundBitmap(musicPicRes);
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


    private Bitmap getForegroundBitmap(int musicPicRes) {
        int screenWidth = DisplayUtil.getScreenWidth(getContext());
        int screenHeight = DisplayUtil.getScreenHeight(getContext());

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(getResources(), musicPicRes, options);
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;

        if (imageWidth < screenWidth && imageHeight < screenHeight) {
            return BitmapFactory.decodeResource(getResources(), musicPicRes);
        }

        int sample = 2;
        int sampleX = imageWidth / DisplayUtil.getScreenWidth(getContext());
        int sampleY = imageHeight / DisplayUtil.getScreenHeight(getContext());

        if (sampleX > sampleY && sampleY > 1) {
            sample = sampleX;
        } else if (sampleY > sampleX && sampleX > 1) {
            sample = sampleY;
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = sample;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        return BitmapFactory.decodeResource(getResources(), musicPicRes, options);
    }
}
