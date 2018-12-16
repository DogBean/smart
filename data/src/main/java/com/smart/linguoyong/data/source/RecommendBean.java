package com.smart.linguoyong.data.source;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecommendBean {


    public void setCover(String cover) {
        this.cover = cover;
    }

    private String cover;
    private String param;

    private String name;
    private int play;
    private int danmaku;
    private int reply;
    private int favourite;

    public void setPlayBeans(List<PlayBean> playBeans) {
        this.playBeans = playBeans;
    }

    private List<PlayBean> playBeans;



    public List<PlayBean> getRecommendList() {
        return playBeans;
    }

    public String cover() {
        return cover;
    }

    public static class PlayBean {
        public String title;
        private String uri;
        @SerializedName("goto")
        private String gotoX;
        public float playtime;

        public PlayBean(String title, float playtime) {
            this.title = title;
            this.playtime = playtime;
        }
    }


}