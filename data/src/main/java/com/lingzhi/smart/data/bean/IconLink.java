package com.lingzhi.smart.data.bean;

public final class IconLink extends Resource {
    private String go;       // intent when forward is clicked

    private String back;     // intent when backward button is clicked


    public String getGo() {
        return go;
    }

    public void setGo(String go) {
        this.go = go;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }


}
