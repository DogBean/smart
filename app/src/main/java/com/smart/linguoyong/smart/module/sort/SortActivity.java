package com.smart.linguoyong.smart.module.sort;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.flyco.tablayout.SlidingTabLayout;
import com.smart.linguoyong.smart.R;
import com.smart.linguoyong.smart.base.RxBaseActivity;
import com.smart.linguoyong.smart.view.widget.NoScrollViewPager;

import butterknife.BindView;

public class SortActivity extends RxBaseActivity {
    @BindView(R.id.sort_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.sort_sliding_tabs)
    SlidingTabLayout mSlidingTabs;
    @BindView(R.id.sort_appbar_layout)
    AppBarLayout mAppbarLayout;
    @BindView(R.id.sort_view_pager)
    NoScrollViewPager mViewPager;

    private String[] titles = new String[]{"原创", "全站", "番剧"};
    private String[] orders = new String[]{"origin-03.json", "all-03.json", "all-3-33.json"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sort;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mViewPager.setAdapter(new SortPagerAdapter(getSupportFragmentManager(), titles, orders));
        mViewPager.setOffscreenPageLimit(orders.length);
        mSlidingTabs.setViewPager(mViewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("排行榜");
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private static class SortPagerAdapter extends FragmentStatePagerAdapter {
        private String[] titles;
        private String[] orders;

        SortPagerAdapter(FragmentManager fm, String[] titles, String[] orders) {
            super(fm);
            this.titles = titles;
            this.orders = orders;
        }

        @Override
        public Fragment getItem(int position) {
            return SortFragment.newInstance("");
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
