package com.lingzhi.smart.data.bean;

public final class ResourceList extends ResourceGroup<Resource> {
    /**
     * header icon url.
     */
    private String header;

    /**
     * footer icon url.
     */
    private String footer;

    /**
     * left icon url.
     */
    private String left;

    private long amount;

    private boolean hasMore;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
}
