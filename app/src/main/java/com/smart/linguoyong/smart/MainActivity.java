package com.smart.linguoyong.smart;

import android.os.Bundle;

import com.smart.linguoyong.smart.base.RxBaseActivity;
import com.smart.linguoyong.smart.main.MainFragment;
import com.smart.linguoyong.smart.main.MainPresenter;
import com.smart.linguoyong.smart.utils.Injection;

public class MainActivity extends RxBaseActivity {
    private static final String TAG = "MainActivity";
    private MainPresenter mMainPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initFragment();
    }

    private void initFragment() {
        MainFragment mMainFragment = MainFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, mMainFragment)
                .show(mMainFragment).commit();

        // Create the presenter
        mMainPresenter = new MainPresenter(Injection.provideMainRepository(getApplicationContext()),
                mMainFragment,
                false,
                Injection.provideSchedulerProvider());
    }

    @Override
    public void initToolBar() {

    }

}
