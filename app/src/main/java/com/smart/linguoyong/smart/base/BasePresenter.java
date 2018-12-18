package com.smart.linguoyong.smart.base;

import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter implements MvpPresenter {
    @NonNull
    protected CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        if (disposables != null && disposables.size() > 0){
            disposables.clear();
        }
    }
}
