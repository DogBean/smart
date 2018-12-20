package com.lingzhi.smart.data.bean;

public class AlbumBean {

   private String id;
   private String imageUrl;
   private String name;
   private int contentNum;
   private int playNum;
   public AlbumBean(){}

    public AlbumBean(String id, String imageUrl, String name, int contentNum, int playNum) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.contentNum = contentNum;
        this.playNum = playNum;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContentNum() {
        return contentNum;
    }

    public void setContentNum(int contentNum) {
        this.contentNum = contentNum;
    }

    public int getPlayNum() {
        return playNum;
    }

    public void setPlayNum(int playNum) {
        this.playNum = playNum;
    }
}
