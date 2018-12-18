package com.smart.linguoyong.smart.module.music;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.smart.linguoyong.smart.base.BasePresenter;
import com.smart.linguoyong.smart.base.BaseView;
import com.smart.linguoyong.smart.module.music.model.Song;
import com.smart.linguoyong.smart.module.music.player.PlayMode;
import com.smart.linguoyong.smart.module.music.player.PlaybackService;

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

    interface Presenter extends BasePresenter {

        void retrieveLastPlayMode();

        void setSongAsFavorite(Song song, boolean favorite);

        void bindPlaybackService();

        void unbindPlaybackService();
    }
}
