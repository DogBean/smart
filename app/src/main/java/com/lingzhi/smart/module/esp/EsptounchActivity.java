package com.lingzhi.smart.module.esp;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.espressif.iot.esptouch.util.ByteUtil;
import com.lingzhi.smart.R;

public class EsptounchActivity extends AppCompatActivity {

    private android.support.v4.app.FragmentManager fm;
    private EspFailFragment espFailFragment;
    private EspPreFragment espPreFragment;
    private EspSettingFragment espSettingFragment;
    private EspTouchingFragment espTouchingFragment;
    private static final int REQUEST_PERMISSION = 0x01;
    private boolean mReceiverRegistered = false;
    private static final String TAG = "EsptounchActivity";
    private boolean mDestroyed = false;
    private TextView mApSsidTV;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                return;
            }

            switch (action) {
                case WifiManager.NETWORK_STATE_CHANGED_ACTION:
                    WifiInfo wifiInfo = intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
                    onWifiChanged(wifiInfo);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esptounch);

        if (Build.VERSION.SDK_INT >= 28) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = {
                        Manifest.permission.ACCESS_COARSE_LOCATION
                };

                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION);
            } else {
                registerBroadcastReceiver();
            }

        } else {
            registerBroadcastReceiver();
        }


        espPreFragment = new EspPreFragment();
        espFailFragment = new EspFailFragment();
        espSettingFragment = new EspSettingFragment();
        espTouchingFragment = new EspTouchingFragment();

        fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, espPreFragment);
        ft.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (!mDestroyed) {
                        registerBroadcastReceiver();
                    }
                }
                break;
        }
    }

    private void onWifiChanged(WifiInfo info) {
        if (info == null) {
            mApSsidTV.setTag(null);

            Log.e(TAG, "onWifiChanged fail info is null");
        } else {
            String ssid = info.getSSID();
            if (ssid.startsWith("\"") && ssid.endsWith("\"")) {
                ssid = ssid.substring(1, ssid.length() - 1);
            }
//            mApSsidTV.setText(ssid);
//            mApSsidTV.setTag(ByteUtil.getBytesByString(ssid));
//            byte[] ssidOriginalData = EspUtils.getOriginalSsidBytes(info);
//            mApSsidTV.setTag(ssidOriginalData);
//
//            String bssid = info.getBSSID();
//            mApBssidTV.setText(bssid);
//
//            mConfirmBtn.setEnabled(true);
//            mMessageTV.setText("");
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                int frequence = info.getFrequency();
//                if (frequence > 4900 && frequence < 5900) {
//                    // Connected 5G wifi. Device does not support 5G
//                    mMessageTV.setText(R.string.wifi_5g_message);
//                }
//            }
        }
    }


    private void registerBroadcastReceiver() {
        IntentFilter filter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(mReceiver, filter);
        mReceiverRegistered = true;
    }

    public void fail() {
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, espFailFragment);
        ft.commit();
    }

    public void setting() {
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, espSettingFragment);
        ft.commit();
    }


    public void touching() {
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, espTouchingFragment);
        ft.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mDestroyed = true;
        if (mReceiverRegistered) {
            unregisterReceiver(mReceiver);
        }
    }
}
