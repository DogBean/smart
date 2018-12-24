package com.lingzhi.smart.data.source.remote;


import com.lingzhi.smart.data.bean.DatedLinkGroup;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @Description:
 * @Author Guoyong.Lin
 * @Time 2018/12/23
 */
public interface Api {


    @GET("/v1/smart/banner")
    Flowable<Resp<DatedLinkGroup>> banner();

    @GET("/v1/smart/category/menu")
    Flowable<Resp<DatedLinkGroup>> mainCategory();


    @GET("/v1/smart/recommend")
    Flowable<Resp<DatedLinkGroup>> mainRecommend();


}
