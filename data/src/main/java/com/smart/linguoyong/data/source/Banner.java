package com.smart.linguoyong.data.source;

import java.util.List;

public class Banner {
    private List<BannerEntity> top;

    public List<BannerEntity> getTop() {
        return top;
    }

    public void setTop(List<BannerEntity> top) {
        this.top = top;
    }

    public static class BannerEntity {
        public String title;
        public String img;
        public String link;

        public BannerEntity(String link, String title, String img) {
            this.link = link;
            this.title = title;
            this.img = img;
        }

    }

}
