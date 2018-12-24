package com.ebensz.shop.net.interceptor;


import com.ebensz.shop.net.https.Authorization;
import com.ebensz.shop.net.utils.ApiConstants;
import com.ebensz.shop.net.utils.AppUtils;
import com.ebensz.shop.net.utils.Constants;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CommonInterceptor implements Interceptor {
    private static final String APPID = "19215EBB0D54D46104526B328FD49DEA";
    private static final String TOKEN = "74703F88823E181FF1C4CCCED3C9CD5C";

    public CommonInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        // 添加新的参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder();
        authorizedUrlBuilder
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host());

        Request newRequest = oldRequest.newBuilder().build();
//                .method(oldRequest.method(), oldRequest.body())
//                .url(authorizedUrlBuilder.build())
//                .header(Constants.PARAM_USER_AGENT, ApiConstants.defaultUserAgent())
//                .header(Constants.PARAM_AUTHORIZATION, Authorization.auth(APPID, TOKEN))
//                .header(Constants.PARAM_DID, AppUtils.getSn())
//                .build();

        return chain.proceed(newRequest);
    }
}