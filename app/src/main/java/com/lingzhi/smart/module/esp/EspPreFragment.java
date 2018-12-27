package com.lingzhi.smart.module.esp;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
 * Created by Guoyong.Lin on 2018/12/26
 **/
public class EspPreFragment extends RxLazyFragment {

    @BindView(R.id.esp_iv_backphone)
    ImageView espIvBackphone;
    Unbinder unbinder;
    @BindView(R.id.btn_next)
    Button btnNext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_esp_pre, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActivity instanceof EsptounchActivity) {
                    ((EsptounchActivity) mActivity).setting();
                }

            }
        });
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
