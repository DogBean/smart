package com.lingzhi.smart.module.esp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lingzhi.smart.R;

/**
 * Created by Guoyong.Lin on 2018/12/26
 **/
public class EspSettingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_esp_setting, container, false);
    }
}
