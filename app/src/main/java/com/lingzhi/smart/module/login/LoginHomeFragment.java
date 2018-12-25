package com.lingzhi.smart.module.login;


import android.view.View;
import android.widget.Button;

import com.lingzhi.smart.R;


public class LoginHomeFragment extends BaseFragment {
    private Button btnLogin;

    @Override
    protected int bindLayout() {
        return R.layout.fragment_login_home;
    }

    @Override
    protected void initView() {
        btnLogin = getContentView().findViewById(R.id.login_btn);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof LoginActivity) {
                    LoginActivity loginActivity = (LoginActivity) mContext;
                    loginActivity.toPhoneEdit();
                }
            }
        });
    }
}
