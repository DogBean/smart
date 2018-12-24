package com.lingzhi.smart.module.music;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.lingzhi.smart.module.music.model.Song;
import com.lingzhi.smart.module.music.player.PlayMode;
import com.lingzhi.smart.module.music.player.PlaybackService;

import rx.subscriptions.CompositeSubscription;

/**
 * @Description:
 * @Author Guoyong.Lin
 * @Time 2018/12/18
 */
public class MusicPlayerPresenter implements MusicPlayerContract.Presenter {
    private Context mContext;
    private MusicPlayerContract.View mView;
    private CompositeSubscription mSubscriptions;


    public MusicPlayerPresenter(Context context, MusicPlayerContract.View view) {
        mContext = context;
        mView = view;
        mSubscriptions = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        bindPlaybackService();

        retrieveLastPlayMode();

        // TODO
//        mView.onSongUpdated(mPlaybackService.getPlayingSong());

    }

    @Override
    public void unsubscribe() {
        unbindPlaybackService();
        // Release context reference
        mContext = null;
        mView = null;
        mSubscriptions.clear();
    }

    @Override
    public void retrieveLastPlayMode() {
        PlayMode lastPlayMode = PreferenceManager.lastPlayMode(mContext);
        mView.updatePlayMode(lastPlayMode);
    }


    @Override
    public void bindPlaybackService() {

    }

    @Override
    public void unbindPlaybackService() {

    }
}
