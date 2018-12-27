package com.lingzhi.smart.data.request;

import com.lingzhi.smart.data.bean.ResourceGroup;
import com.lingzhi.smart.data.bean.Song;

import java.util.ArrayList;

/**
 * @Description:
 * @Author Guoyong.Lin
 * @Time 2018/12/27
 */
public class AlbumRequest {
    int oid;
    ResourceGroup<Song> group;

    public AlbumRequest(int oid, ResourceGroup<Song> group) {
        this.oid = oid;
        this.group = group;
    }
}
