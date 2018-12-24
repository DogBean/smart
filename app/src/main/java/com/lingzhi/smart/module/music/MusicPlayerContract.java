package com.lingzhi.smart.module.music;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lingzhi.smart.base.BasePresenter;
import com.lingzhi.smart.base.BaseView;
import com.lingzhi.smart.base.MvpPresenter;
import com.lingzhi.smart.module.music.model.Song;
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

        void onSongSetAsFavorite(@NonNull Song song);

        void onSongUpdated(@Nullable Song song);

        void updatePlayMode(PlayMode playMode);

        void updatePlayToggle(boolean play);

        void updateFavoriteToggle(boolean favorite);
    }

    interface Presenter extends MvpPresenter {

        void retrieveLastPlayMode();

        void bindPlaybackService();

        void unbindPlaybackService();
    }
}
