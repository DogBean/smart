package com.lingzhi.smart.module.main;

import android.support.annotation.NonNull;
import android.util.Log;

import com.lingzhi.smart.R;
import com.lingzhi.smart.app.SmartApplication;
import com.lingzhi.smart.data.bean.DatedLinkGroup;
import com.lingzhi.smart.data.bean.IconLink;
import com.lingzhi.smart.data.bean.Resource;
import com.lingzhi.smart.data.bean.ResourceList;
import com.lingzhi.smart.data.source.Banner;
import com.lingzhi.smart.data.source.MainRepository;
import com.lingzhi.smart.data.source.remote.Resp;
import com.lingzhi.smart.utils.schedulers.BaseSchedulerProvider;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainPresenter implements MainContract.Presenter {

    @NonNull
    private final MainContract.View mView;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;


    @NonNull
    private CompositeDisposable mCompositeDisposable;

    private MainRepository mMainRepository;


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
        Disposable disposable = Flowable.zip(
                mMainRepository.topic(),
                mMainRepository.getBanner(),
                mMainRepository.requisite(),
                mMainRepository.recommend(),
                (Function4<Resp<DatedLinkGroup>, Resp<DatedLinkGroup>, Resp<ResourceList>, Resp<ResourceList>, Object>)
                        (topic, banner, requisite, recommends) -> {
                            if (topic != null && topic.isSuccess()) {
                                mView.category(topic.getData());
                            }
                            if (banner != null && banner.isSuccess()) {
                                DatedLinkGroup linkGroup = banner.getData();
                                IconLink[] links = linkGroup.getLinks();
                                List<Banner.BannerEntity> list = new ArrayList<>();
                                for (IconLink link : links) {
                                    list.add(new Banner.BannerEntity(link.getIcon(), "Test", link.getIcon()));
                                    mView.banner(list);
                                }
                            }

                            if (recommends != null && recommends.isSuccess()) {
                                mView.recommend(recommends.getData());
                            }

                            if (requisite != null && requisite.isSuccess()) {
                                mView.requisite(requisite.getData());
                                mMainRepository.insertRequisite(requisite.getData());
                            }
                            return Resp.ok();
                        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                mView.finishTask();
            }
        });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }
}
