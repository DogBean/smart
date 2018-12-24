package com.lingzhi.smart.data.source.remote;

import com.ebensz.shop.net.ApiClient;
import com.lingzhi.smart.data.bean.DatedLinkGroup;

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
    public static String BASE_URL = "http://192.168.0.156:8080";

    public static final String FORMATE = "json";
    public static final String BASE = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.6.5.6&format=" + FORMATE;


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

    public static Flowable<Resp<DatedLinkGroup>> banner() {
        return getApi()
                .banner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
