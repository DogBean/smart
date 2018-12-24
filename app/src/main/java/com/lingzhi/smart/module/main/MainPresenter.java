package com.lingzhi.smart.module.main;

import android.support.annotation.NonNull;
import android.util.Log;

import com.lingzhi.smart.app.SmartApplication;
import com.lingzhi.smart.data.bean.DatedLinkGroup;
import com.lingzhi.smart.data.bean.IconLink;
import com.lingzhi.smart.data.source.Banner;
import com.lingzhi.smart.data.source.MainRepository;
import com.lingzhi.smart.data.source.RecommendBean;
import com.lingzhi.smart.data.source.remote.Resp;
import com.lingzhi.smart.utils.schedulers.BaseSchedulerProvider;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainPresenter implements MainContract.Presenter {

    @NonNull
    private final MainContract.View mView;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;


    @NonNull
    private CompositeDisposable mCompositeDisposable;

    private MainRepository mMainRepository;

    /**
     * Creates a presenter for the add/edit view.
     *
     * @param taskId                 ID of the task to edit or null for a new task
     * @param tasksRepository        a repository of data for tasks
     * @param addTaskView            the add/edit view
     * @param shouldLoadDataFromRepo whether data needs to be loaded or not (for config changes)
     */
    public MainPresenter(
            MainRepository mainRepository,
            @NonNull MainContract.View addTaskView, boolean shouldLoadDataFromRepo,
            @NonNull BaseSchedulerProvider schedulerProvider) {
        mMainRepository = mainRepository;
        mView = checkNotNull(addTaskView);
        mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null!");
        mCompositeDisposable = new CompositeDisposable();
        mView.setPresenter(this);
    }


    @Override
    public void subscribe() {
        loadData();
    }

    private void loadData() {
        Disposable disposable = Flowable.combineLatest(mMainRepository.getRecommendList(), mMainRepository.getBanner(), new BiFunction<List<RecommendBean>, Resp<DatedLinkGroup>, Object>() {
            @Override
            public Object apply(List<RecommendBean> recommendBeans, Resp<DatedLinkGroup> datedLinkGroupResp) throws Exception {
                mView.setRecommedSection(recommendBeans);

                if (datedLinkGroupResp != null && datedLinkGroupResp.isSuccess()) {
                    DatedLinkGroup linkGroup = datedLinkGroupResp.getData();
                    IconLink[] links = linkGroup.getLinks();
                    List<Banner.BannerEntity> list = new ArrayList<>();
                    for (IconLink link : links) {
                        list.add(new Banner.BannerEntity(link.getIcon(), "Test", link.getIcon()));
                        mView.banner(list);
                    }
                }
                return Resp.ok();
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                mView.finishTask();
            }
        });

    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }
}
