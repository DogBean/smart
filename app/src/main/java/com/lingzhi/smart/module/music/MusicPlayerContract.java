package com.lingzhi.smart.module.music;

import android.support.annotation.Nullable;

import com.lingzhi.smart.base.BaseView;
import com.lingzhi.smart.base.MvpPresenter;
import com.lingzhi.smart.module.music.player.PlayMode;
import com.lingzhi.smart.module.music.player.PlaybackService;

/**
 * @Description:
 * @Author Guoyong.Lin
 * @Time 2018/12/18
 */
public interface MusicPlayerContract {

    interface View extends BaseView<Presenter> {

        void handleError(Throwable error);

        void onPlaybackServiceBound(PlaybackService service);

        void onPlaybackServiceUnbound();

        void onSongUpdated(@Nullable Song song);

        void updatePlayMode(PlayMode playMode);

        void updatePlayToggle(boolean play);

    }

    interface Presenter extends MvpPresenter {

        void retrieveLastPlayMode();

        void bindPlaybackService();

        void unbindPlaybackService();
    }
}
