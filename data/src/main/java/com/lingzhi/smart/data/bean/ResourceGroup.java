package com.lingzhi.smart.data.bean;

public class ResourceGroup<T extends Resource> extends Resource {
    private T[] children;

    public T[] getChildren() {
        return children;
    }

    public void setChildren(T[] children) {
        this.children = children;
    }
}
