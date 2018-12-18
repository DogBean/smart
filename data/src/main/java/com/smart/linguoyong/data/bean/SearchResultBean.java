package com.smart.linguoyong.data.bean;

import java.util.ArrayList;
import java.util.List;

public class SearchResultBean {
    private int albumTotal;
    private int audioTotal;
    private List<AlbumBean> albumBeanList = new ArrayList<>();
    private List<AudioBean> audioBeanList = new ArrayList<>();

    public SearchResultBean(){}
    public SearchResultBean(int albumTotal, int audioTotal, List<AlbumBean> albumBeanList, List<AudioBean> audioBeanList) {
        this.albumTotal = albumTotal;
        this.audioTotal = audioTotal;
        this.albumBeanList = albumBeanList;
        this.audioBeanList = audioBeanList;
    }

    public int getAlbumTotal() {
        return albumTotal;
    }

    public void setAlbumTotal(int albumTotal) {
        this.albumTotal = albumTotal;
    }

    public int getAudioTotal() {
        return audioTotal;
    }

    public void setAudioTotal(int audioTotal) {
        this.audioTotal = audioTotal;
    }

    public List<AlbumBean> getAlbumBeanList() {
        return albumBeanList;
    }

    public void setAlbumBeanList(List<AlbumBean> albumBeanList) {
        this.albumBeanList = albumBeanList;
    }

    public List<AudioBean> getAudioBeanList() {
        return audioBeanList;
    }

    public void setAudioBeanList(List<AudioBean> audioBeanList) {
        this.audioBeanList = audioBeanList;
    }
}
