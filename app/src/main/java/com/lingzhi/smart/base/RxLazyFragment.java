package com.lingzhi.smart.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
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
    Unbinder unbinder;

    private CompositeSubscription mSubscriptions;
    protected Activity mActivity;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addSubscription(subscribeEvents());
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onAttach(Activity activity) {
        mActivity = activity;
        super.onAttach(activity);
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
        unbinder.unbind();
        super.onDestroyView();
        if (mSubscriptions != null) {
            mSubscriptions.clear();
        }
    }
}
