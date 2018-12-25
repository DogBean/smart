package com.lingzhi.smart.module.login;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.lingzhi.smart.R;

public class LoginActivity extends AppCompatActivity {
    private FragmentManager fm;
    private LoginHomeFragment mLoginHomeFragment;
    private LoginPhoneFragment mLoginPhoneFragment;
    private LoginCodeFragment mLoginCodeFragment;
    private Fragment mCurrent;
    public static String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        fm = getSupportFragmentManager();
        mLoginHomeFragment = new LoginHomeFragment();
        mLoginPhoneFragment = new LoginPhoneFragment();
        mLoginCodeFragment = new LoginCodeFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.login_rl_content, mLoginHomeFragment);
        ft.commit();
    }

    private void initView() {

    }

    public void toPhoneEdit() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.login_rl_content, mLoginPhoneFragment);
        ft.commit();
    }

    public void backLoginFirst() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.login_rl_content, mLoginHomeFragment);
        ft.commit();
    }

    public void toMsgCodeEdit() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.login_rl_content, mLoginCodeFragment);
        ft.commit();
    }


}