package com.ebensz.shop.net.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.ebensz.shop.net.RxRetrofitApp;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Guoyong.Lin on 2018/3/6.
 */

public class AppUtils {
    private static final String UNKONW = "unkonw";
    private static final String SN_KEY = "gsm.scril.sn";
    private static final String NV2497SN_KEY = "persist.vendor.radio.nv2497sn";
    public static int versionCode = 0;
    public static String appDate = null;
    public static String versionName = null;
    private static String sn = null;
    private static String userAgent = null;

    public static void check(Context context) {
        if (versionCode == 0) {
            try {
                PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                long created = pi.lastUpdateTime;
                if (created == 0) {
                    created = pi.firstInstallTime;
                }
                appDate = Build.TIME + "T" + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(new Date(created));
                versionName = pi.versionName;
                versionCode = pi.versionCode;
            } catch (Throwable e) {
                Log.e(ApiConstants.TAG, "check app info exception", e);
            }
        }
    }


    public static String getSn( ) {
//        if (TextUtils.isEmpty(sn)) {
//            sn = SystemPropertiesProxy.get(RxRetrofitApp.getApplication(), SN_KEY);
//            if (TextUtils.isEmpty(sn)) {
//                sn = SystemPropertiesProxy.get(RxRetrofitApp.getApplication(), NV2497SN_KEY);
//            }
//            if (TextUtils.isEmpty(sn)) {
//                sn = UNKONW;
//            }
//        }
        return "test";
    }


    public static String defaultUserAgent() {
        if (userAgent == null) {
            userAgent = System.getProperty("http.agent", null);
            if (userAgent == null) {
                userAgent = "Java" + System.getProperty("java.version");
            }
        }
        return userAgent;
    }
    public static long getTimeStamps() {
        return SystemClock.uptimeMillis() / 1000;
    }

}
