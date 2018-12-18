package com.smart.linguoyong.data.bean;

public class AudioBean {

   private String id;
   private String imageUrl;
   private String playUrl;
   private String name;
   private int playNum;
   private String time;
   public AudioBean(){}

    public AudioBean(String id, String imageUrl, String playUrl, String name, int playNum,String time) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.playUrl = playUrl;
        this.name = name;
        this.playNum = playNum;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayNum() {
        return playNum;
    }

    public void setPlayNum(int playNum) {
        this.playNum = playNum;
    }
}
