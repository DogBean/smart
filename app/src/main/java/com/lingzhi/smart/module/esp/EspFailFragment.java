package com.lingzhi.smart.module.esp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.lingzhi.smart.R;
import com.lingzhi.smart.base.RxLazyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Description:
 * @Author Guoyong.Lin
 * @Time 2018/12/26
 */
public class EspFailFragment extends RxLazyFragment {

    @BindView(R.id.esp_fail_iv_backphone)
    ImageView back;
    @BindView(R.id.confirm_btn)
    Button confirm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_esp_fail, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
