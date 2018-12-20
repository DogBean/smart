package com.lingzhi.smart.app;

import android.app.Application;

import com.smart.linguoyong.data.utils.SPUtils;
import com.lingzhi.smart.BuildConfig;
import com.lingzhi.smart.utils.ScreenAdapter;
import com.lingzhi.smart.utils.ToastUtils;
import com.socks.library.KLog;

/**
 * Created by cs on 16/12 2018.
 */
public class SmartApplication extends Application {

    private static SmartApplication sInstance;

    public static final String TAG = "Smart";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //屏幕适配
        ScreenAdapter.init(this, 375, ScreenAdapter.MATCH_BASE_WIDTH, ScreenAdapter.MATCH_UNIT_DP);
        //sp初始化
        SPUtils.init(this);
        KLog.init(BuildConfig.DEBUG,"SmartTag");
        ToastUtils.init(this);
    }


    private static SmartApplication instance;

    public static SmartApplication getInstance() {
        return instance;
    }
}
