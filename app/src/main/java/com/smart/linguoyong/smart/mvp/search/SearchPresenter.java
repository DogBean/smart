package com.smart.linguoyong.smart.mvp.search;

import com.smart.linguoyong.smart.base.BasePresenter;

import static com.google.common.base.Preconditions.checkNotNull;

public class SearchPresenter  implements SearchContract.Presenter {
    SearchContract.View mView;
    public SearchPresenter(SearchContract.View view){
        mView = checkNotNull(view);
    }
    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void search(String key) {

    }

    @Override
    public void getSearchHotTag(String key) {

    }

    @Override
    public void insertSearchHotTag(String tag) {

    }

    @Override
    public void getSearchNearlyTag() {

    }

    @Override
    public void insertSearchNearlyTag(String tag) {

    }

    @Override
    public void clearSearchNearlyTag() {

    }
}
