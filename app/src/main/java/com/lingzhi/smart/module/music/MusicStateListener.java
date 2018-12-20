package com.lingzhi.smart.module.music;

/**
 * Created by Guoyong.Lin on 2018/12/17
 **/
public interface MusicStateListener {

    /**
     * 更新歌曲状态信息
     */
    void updateTrackInfo();

    void updateTime();

    void changeTheme();

    void reloadAdapter();
}
