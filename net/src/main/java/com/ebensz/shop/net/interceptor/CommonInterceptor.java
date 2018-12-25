package com.ebensz.shop.net.interceptor;


import com.ebensz.shop.net.https.Authorization;
import com.ebensz.shop.net.utils.ApiConstants;
import com.ebensz.shop.net.utils.AppUtils;
import com.ebensz.shop.net.utils.Constants;
import com.ebensz.shop.net.utils.SPUtils;
import com.ebensz.shop.net.utils.UUIDGenerator;
import com.ebensz.shop.net.utils.Utils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CommonInterceptor implements Interceptor {

    public CommonInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request old = chain.request();

        String token = SPUtils.getInstance().getString(Constants.TOKEN);
        String nonc = UUIDGenerator.generator();
        String signature = Utils.getSignature(token, nonc);

        Request.Builder newRequest = old.newBuilder();
        String method = old.method();
        if ("GET".equals(method) || "DELETE".equals(method) ||
                "HEAD".equals(method) || "OPTION".equals(method)) {
            // 添加新的参数
            HttpUrl.Builder url = old.url().newBuilder();

            if (token != null) {
                url.addQueryParameter(Constants.TOKEN, token);
            }

            if (nonc != null) {
                url.addQueryParameter(Constants.NONC, nonc);
            }
            if (signature != null) {
                url.addQueryParameter(Constants.SIGNATURE, signature);
            }
            newRequest.url(url.build());
        }

        return chain.proceed(newRequest.build());
    }
}