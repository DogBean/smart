package com.lingzhi.smart.app;

import android.app.Application;

import com.ebensz.shop.net.RxRetrofitApp;
import com.lingzhi.smart.BuildConfig;
import com.lingzhi.smart.data.utils.SPUtils;
import com.lingzhi.smart.utils.ScreenAdapter;
import com.lingzhi.smart.utils.ToastUtils;
import com.lingzhi.smart.utils.Utils;
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
        KLog.init(BuildConfig.DEBUG, "SmartTag");
        ToastUtils.init(this);
        Utils.init(this);


//        public static final int CMD_LOGIN = 100;
//        public static final int CMD_LOGOUT = 101;
//        public static final int CMD_PLAYURL = 200;
//        public static final int CMD_PLAYSONE = 201;
//        public static final int CMD_GETPLAYLIST = 202;
//        public static final int CMD_GETCURRENTPLAYING = 203;
//        public static final int CMD_SETPLAYMODE = 300;
//        public static final int CMD_SETCHILDLOCK = 301;
//        public static final int CMD_SETEARLIGHT = 302;
//        public static final int CMD_SETLEDLIGHT = 303;
//        public static final int CMD_SETVOLUME = 304;
//        public static final int CMD_PLAY = 305;
//        public static final int CMD_PAUSE = 306;
//        public static final int CMD_PLAYNEXT = 307;
//        public static final int CMD_PLAYPREV = 308;
//        public static final int CMD_GETPLAYMODE = 400;
//        public static final int CMD_GETCHILDLOCKSTATUS = 401;
//        public static final int CMD_GETEARLIGHTSTATUS = 402;
//        public static final int CMD_GETLEDLIGHTSTATUS = 403;
//        public static final int CMD_GETVOLUME = 404;
//        public static final int CMD_GETBATTERY = 405;
//        public static final int CMD_GETPLAYSTATUS = 406;
//        public static final int CMD_GETNEWESTMESSAGE = 500;

//        NettyClient.getInstance().connect(INettyClient.HOST, INettyClient.PORT);
//        NettyClient.getInstance().sendLogin();
//        NettyClient.getInstance().addDataReceiveListener(new INettyClient.OnDataReceiveListener() {
//            @Override
//            public void onDataReceive(Packet packet) {
//                Log.e(TAG, "onDataReceive: -----------" + packet);
//            }
//        });
        RxRetrofitApp.init(this);

    }


    private static SmartApplication instance;

    public static SmartApplication getInstance() {
        return instance;
    }


}
