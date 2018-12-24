package com.lingzhi.smart.data.bean;

public final class DatedLinkGroup {
    private long start = 0;

    private long end = 0;

    private String etag;

    private IconLink[] links;

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public IconLink[] getLinks() {
        return links;
    }

    public void setLinks(IconLink[] links) {
        this.links = links;
    }
}
