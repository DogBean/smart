package com.lingzhi.smart.data.source.remote;

import com.ebensz.shop.net.ApiClient;
import com.lingzhi.smart.data.bean.DatedLinkGroup;
import com.lingzhi.smart.data.bean.ResourceList;

import java.util.List;

import io.reactivex.Flowable;
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
    public static String BASE_URL = "http://192.168.4.152:8080";

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


    public static Flowable<Resp<ResourceList>> requisite() {
        return getApi()
                .requisite()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Flowable<Resp<ResourceList>> recommend() {
        return getApi()
                .recommend()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
