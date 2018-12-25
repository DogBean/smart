package com.lingzhi.smart.module.guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingzhi.smart.R;
import com.lingzhi.smart.module.login.LoginActivity;
import com.lingzhi.smart.utils.Navigator;
import com.lingzhi.smart.utils.SPUtils;
import com.lwj.widget.viewpagerindicator.ViewPagerIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends AppCompatActivity {
    public static final String IS_FIRST = "is_first";
    private static final String TAG = "GuideActivity";
    static int[] ids = new int[]{R.drawable.guide_01, R.drawable.guide_02, R.drawable.guide_03};
    @BindView(R.id.guide_view_pager)
    ViewPager viewPager;
    @BindView(R.id.guide_btn_start_main)
    ImageView btnStartMain;
    @BindView(R.id.guide_text_view)
    TextView textView;

    @BindView(R.id.guide_view_start)
    LinearLayout viewStart;

    @BindView(R.id.indicator_circle_line)
    ViewPagerIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.guide_view_pager);
        btnStartMain = findViewById(R.id.guide_btn_start_main);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new GuideOnPageChangeListener());
        indicator.setViewPager(viewPager);
    }

    @OnClick({R.id.guide_btn_start_main})
    public void startMain() {


        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        finish();
    }

    private static class PagerAdapter extends FragmentPagerAdapter {

        private static final int NUM_PAGES = ids.length;

        private PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(ids[position]);
        }
    }

    class GuideOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == ids.length - 1) {
                viewStart.setVisibility(View.VISIBLE);
            } else {
                viewStart.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
