package com.lingzhi.smart.module.music.event;

import com.lingzhi.smart.module.music.model.PlayList;

public class PlayListNowEvent {

    public PlayList playList;
    public int playIndex;

    public PlayListNowEvent(PlayList playList, int playIndex) {
        this.playList = playList;
        this.playIndex = playIndex;
    }
}