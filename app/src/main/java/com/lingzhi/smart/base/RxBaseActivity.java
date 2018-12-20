package com.lingzhi.smart.base;

import android.os.Bundle;

import com.lingzhi.smart.app.AppManager;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;

public abstract class RxBaseActivity extends RxAppCompatActivity {
    private Unbinder bind;
    private List<Subscription> subscriptions = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        initViews(savedInstanceState);
        initToolBar();
        subscribeRxbus();
    }

    /**
     * 设置布局layout
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化views
     */
    public abstract void initViews(Bundle savedInstanceState);

    /**
     * 初始化toolbar
     */
    public abstract void initToolBar();

    /**
     * 加载数据
     */
    public void loadData() {
    }

    /**
     * 显示进度条
     */
    public void showProgressBar() {
    }

    /**
     * 隐藏进度条
     */
    public void hideProgressBar() {
    }

    /**
     * 初始化recyclerView
     */
    public void initRecyclerView() {
    }

    /**
     * 初始化refreshLayout
     */
    public void initRefreshLayout() {
    }

    /**
     * 设置数据显示
     */
    public void finishTask() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        AppManager.getAppManager().finishActivity(this);
        unsubscribeRxbus();
    }


    protected void subscribeRxbus(){}
    private void unsubscribeRxbus(){
        for(Subscription subscription : subscriptions){
            subscription.unsubscribe();
        }
        subscriptions.clear();
    }
    protected void addSubscribe(Subscription subscription){
        subscriptions.add(subscription);
    }
}
