package com.smart.linguoyong.smart.app;

import android.app.Application;

import com.smart.linguoyong.smart.utils.ScreenAdapter;

/**
 * Created by cs on 16/12 2018.
 */
public class SmartApplication extends Application {

    public static final String TAG = "Smart";

    @Override
    public void onCreate() {
        super.onCreate();
        //屏幕适配
        ScreenAdapter.init(this, 720, ScreenAdapter.MATCH_BASE_WIDTH, ScreenAdapter.MATCH_UNIT_DP);
    }
}
