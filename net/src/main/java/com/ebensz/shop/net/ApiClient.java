package com.ebensz.shop.net;


import android.util.Log;

import com.ebensz.shop.net.https.TrustManagerProxy;
import com.ebensz.shop.net.interceptor.CommonInterceptor;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private static final String TAG = "Net";

    private Retrofit.Builder mRetrofitBuilder;
    private OkHttpClient.Builder mOkHttpClientBuilder;

    public ApiClient() {
        mRetrofitBuilder = new Retrofit.Builder()
                .baseUrl("https://113.106.61.54:8089")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());


        mOkHttpClientBuilder = new OkHttpClient.Builder();
        mOkHttpClientBuilder.readTimeout(6, TimeUnit.SECONDS);
        mOkHttpClientBuilder.connectTimeout(15, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            mOkHttpClientBuilder.addNetworkInterceptor(
                    new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            Log.i(TAG, "net message: " + message);
                        }
                    }).setLevel(HttpLoggingInterceptor.Level.BODY)
            );
        }
    }


    public <S> S createApi(String baseUrl, Class<S> ApiClass) {
        mRetrofitBuilder.baseUrl(baseUrl);

        return createApi(ApiClass);
    }

    public <S> S createApi(Class<S> ApiClass) {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            X509TrustManager x509 = TrustManagerProxy.init();
            sslContext.init(null, new TrustManager[]{x509}, null);

            return mRetrofitBuilder
                    .client(mOkHttpClientBuilder.sslSocketFactory(sslContext.getSocketFactory())
                            .hostnameVerifier(new HostnameVerifier() {
                                @Override
                                public boolean verify(String hostname, SSLSession session) {
                                    return true;
                                }
                            }).build())
                    .build()
                    .create(ApiClass);
        } catch (Exception e) {
            Log.e(TAG, "Net Module createApi ex:", e);
            return mRetrofitBuilder.client(mOkHttpClientBuilder.build()).build().create(ApiClass);
        }
    }

}
