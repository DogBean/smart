package com.lingzhi.smart.module.music;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lingzhi.smart.R;
import com.lingzhi.smart.app.SmartApplication;
import com.lingzhi.smart.module.music.player.IPlayback;
import com.lingzhi.smart.module.music.player.Player;
import com.lingzhi.smart.utils.Navigator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by Guoyong.Lin on 2018/12/17
 **/
public class QuickControlsFragment extends MusicBaseFragment implements IPlayback.Callback {
    private static final String TAG = SmartApplication.TAG;

    @BindView(R.id.ll_play_view)
    RelativeLayout viewPlay;
    @BindView(R.id.image_play_control)
    ImageView mPlayPause;
    Unbinder unbinder;
    @BindView(R.id.playbar_info)
    TextView mTitle;
    @BindView(R.id.play_list)
    ImageView mPlayList;

    public static QuickControlsFragment newInstance() {
        return new QuickControlsFragment();
    }

    private Context mContext;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quick_controls, container, false);
        mContext = getContext();
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }


    @Override
    public void onResume() {
        Player.getInstance().registerCallback(this);
        updateNowPlayingCard();
        super.onResume();
    }

    @Override
    public void onPause() {
        Player.getInstance().unregisterCallback(this);
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ll_play_view)
    public void startToMusicPlay() {
        Navigator.navigateToMusicPlay(getContext());
    }


    @Override
    public void onSwitchLast(@Nullable Song last) {
        Log.d(TAG, "onSwitchLast song:" + (last == null ? null : last.toString()));
        updateNowPlayingCard();

    }

    @Override
    public void onSwitchNext(@Nullable Song next) {
        Log.d(TAG, "onSwitchLast song:" + (next == null ? null : next.toString()));
        updateNowPlayingCard();
    }

    @Override
    public void onComplete(@Nullable Song next) {

    }

    @Override
    public void onPlayStatusChanged(boolean isPlaying) {
        updateTrackInfo();
    }

    public void updateTrackInfo() {
        updateState();
    }

    public void updateNowPlayingCard() {
        Song playingSong = Player.getInstance().getPlayingSong();
        if (playingSong != null) {
            mTitle.setText(playingSong.getDisplayName());
        }
    }

    public void updateState() {
        if (Player.getInstance().isPlaying()) {
            mPlayPause.setImageResource(R.drawable.ic_pause_music);
        } else {
            mPlayPause.setImageResource(R.drawable.ic_play_music);
        }
    }
}
