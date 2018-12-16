package com.smart.linguoyong.smart.mvp.search;

import android.os.Bundle;
import android.view.View;

import com.smart.linguoyong.smart.R;
import com.smart.linguoyong.smart.base.RxBaseActivity;
import com.smart.linguoyong.smart.bean.SearchTagBean;
import com.smart.linguoyong.smart.main.MainContract;
import com.smart.linguoyong.smart.view.SearchEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class SearchActivity extends RxBaseActivity implements SearchContract.View{
    @BindView(R.id.search_input_tv)
    SearchEditText searchEditText;
    SearchContract.Presenter mPresenter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }

    @OnClick({R.id.tv_cancle})
    public void onClick(View view){
        int viewId = view.getId();
        if(viewId == R.id.tv_cancle){

        }
    }

    @Override
    public void showSearchHotTag(List<SearchTagBean> tags) {

    }

    @Override
    public void showSearchNearlyTag(List<String> tags) {

    }

    @Override
    public void clearTagView() {

    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = new SearchPresenter(this);
    }
}
