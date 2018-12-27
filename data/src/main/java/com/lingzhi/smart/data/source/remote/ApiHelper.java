package com.lingzhi.smart.data.source.remote;

import com.ebensz.shop.net.ApiClient;
import com.lingzhi.smart.data.bean.DatedLinkGroup;
import com.lingzhi.smart.data.bean.IconLink;
import com.lingzhi.smart.data.bean.LoginResult;
import com.lingzhi.smart.data.bean.ResourceGroup;
import com.lingzhi.smart.data.bean.ResourceList;
import com.lingzhi.smart.data.bean.Song;
import com.lingzhi.smart.data.request.AccountRequest;
import com.lingzhi.smart.data.request.AlbumRequest;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description:
 * @Author Guoyong.Lin
 * @Time 2018/12/23
 */
public class ApiHelper {
    private static Api browseApi;
    private static Api musicApi;
    public static String BASE_URL = "http://47.106.230.10:80";

    private static Api getMusicApi() {
        if (musicApi == null) {
            musicApi = new ApiClient().createApi(BASE_URL, Api.class);
        }
        return musicApi;
    }

    private static Api getApi() {
        if (browseApi == null) {
            browseApi = new ApiClient().createApi(BASE_URL, Api.class);
        }
        return browseApi;
    }

    public static Flowable<Resp<DatedLinkGroup>> topic() {
        return getApi()
                .topic()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Flowable<Resp<DatedLinkGroup>> banner() {
        return getApi()
                .banner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static Flowable<Resp<ResourceGroup<Song>>> requisite() {
        return getApi()
                .requisite()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Flowable<Resp<ResourceGroup<IconLink>>> recommend() {
        return getApi()
                .recommend()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Flowable<Resp<ResourceGroup<Song>>> album(int cid) {
        return getApi()
                .album(cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Flowable<Resp> updateAlbum(AlbumRequest request) {
        return getApi()
                .updateAlbum(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Flowable<Resp<String>> play(int playListId, int songId) {
        return getApi()
                .play(playListId, songId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Flowable<Resp<String>> prev(int playListId, int songId) {
        return getApi()
                .prev(playListId, songId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Flowable<Resp<String>> next(int playListId, int songId) {
        return getApi()
                .next(playListId, songId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Flowable<Resp<LoginResult>> login(AccountRequest request) {
        return new ApiClient().createApi("http://192.168.4.152:805", Api.class)
                .login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
