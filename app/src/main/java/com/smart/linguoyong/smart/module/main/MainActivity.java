package com.smart.linguoyong.smart.module.main;

import android.content.Intent;
import android.os.Bundle;

import com.smart.linguoyong.smart.R;
import com.smart.linguoyong.smart.base.RxBaseActivity;
import com.smart.linguoyong.smart.module.guide.GuideActivity;
import com.smart.linguoyong.smart.module.main.MainFragment;
import com.smart.linguoyong.smart.module.main.MainPresenter;
import com.smart.linguoyong.smart.module.search.SearchActivity;
import com.smart.linguoyong.smart.utils.Injection;
import com.smart.linguoyong.smart.utils.Navigator;
import com.smart.linguoyong.smart.utils.SPUtils;

public class MainActivity extends RxBaseActivity {
    private static final String TAG = "MainActivity";
    private MainPresenter mMainPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        if (SPUtils.getInstance().getBoolean(GuideActivity.IS_FIRST, true)) {
            Navigator.navigateToGuide(this);
            finish();
            return;
        }
        initFragment();
    }

    private void initFragment() {
        MainFragment mMainFragment = MainFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, mMainFragment)
                .show(mMainFragment).commit();

        // Create the presenter
        mMainPresenter = new MainPresenter(Injection.provideMainRepository(),
                mMainFragment,
                false,
                Injection.provideSchedulerProvider());
    }

    @Override
    public void initToolBar() {

    }

    @Override
    protected void onResume() {
        super.onResume();
//        startActivity(new Intent(this,SearchActivity.class));
    }
}
