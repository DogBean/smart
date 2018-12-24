package com.lingzhi.smart.module.main;

import android.os.Bundle;

import com.lingzhi.smart.R;
import com.lingzhi.smart.base.RxBaseActivity;
import com.lingzhi.smart.module.guide.GuideActivity;
import com.lingzhi.smart.utils.Injection;
import com.lingzhi.smart.utils.Navigator;
import com.lingzhi.smart.utils.SPUtils;

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

        showQuickControl(true);
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
