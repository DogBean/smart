package com.lingzhi.smart.data.source.remote;


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
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @Description:
 * @Author Guoyong.Lin
 * @Time 2018/12/23
 */
public interface Api {


    @GET("/v1/smart/banner")
    Flowable<Resp<DatedLinkGroup>> banner();

    @GET("/v1/smart/topic")
    Flowable<Resp<DatedLinkGroup>> topic();


    @GET("/v1/smart/requisite")
    Flowable<Resp<ResourceGroup<Song>>> requisite();

    @GET("/v1/smart/recommend")
    Flowable<Resp<ResourceGroup<IconLink>>> recommend();

    @GET("/v1/smart/album/{albumId}")
    Flowable<Resp<ResourceGroup<Song>>> album(@Path("albumId") int oid);

    @PUT("/v1/smart/album/{albumId}")
    Flowable<Resp> updateAlbum(@Body AlbumRequest request);

    @POST("/v1/account/user")
    Flowable<Resp<LoginResult>> login(@Body AccountRequest req);



//     音乐控制上传接口
    @GET("/v1/smart/command/play/{playListId}/{songId}")
    Flowable<Resp<String>> play(@Path("playListId") int oid, @Path("songId") int songId);


    @GET("/v1/smart/command/prev/{playListId}/{songId}")
    Flowable<Resp<String>> prev(@Path("playListId") int oid, @Path("songId") int songId);


    @GET("/v1/smart/command/next/{playListId}/{songId}")
    Flowable<Resp<String>> next(@Path("playListId") int oid, @Path("songId") int songId);

}
