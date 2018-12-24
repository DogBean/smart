package com.lingzhi.smart.data.bean;

public class Resource {
    private long id;

    /**
     * topic id.
     */
    private int cid;

    /**
     * parent catetory id.
     */
    private int pcid;

    private String icon;

    private String name;

    public String getOname() {
        return oname;
    }

    private String oname;

    private String desc;

    private String durl;

    public long getDuration() {
        return duration;
    }

    private long duration;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getPcid() {
        return pcid;
    }

    public void setPcid(int pcid) {
        this.pcid = pcid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDurl() {
        return durl;
    }

    public void setDurl(String durl) {
        this.durl = durl;
    }
}
