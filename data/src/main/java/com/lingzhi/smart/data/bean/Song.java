package com.lingzhi.smart.data.bean;

public final class Song extends Resource {
    private String album;

    private String author;

    private long albumId;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    private long duration;

    private long size;

}
