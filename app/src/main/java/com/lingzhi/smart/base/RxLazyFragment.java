package com.lingzhi.smart.base;

import android.os.Bundle;
import android.view.View;

import com.trello.rxlifecycle.components.support.RxFragment;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by hcc on 16/8/7 21:18
 * 100332338@qq.com
 * <p/>
 * Fragment基类
 */
public class RxLazyFragment extends RxFragment {

    public MvpPresenter mPresenter;

    private CompositeSubscription mSubscriptions;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addSubscription(subscribeEvents());
    }

    protected Subscription subscribeEvents() {
        return null;
    }

    protected void addSubscription(Subscription subscription) {
        if (subscription == null) return;
        if (mSubscriptions == null) {
            mSubscriptions = new CompositeSubscription();
        }
        mSubscriptions.add(subscription);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mSubscriptions != null) {
            mSubscriptions.clear();
        }
    }
}
