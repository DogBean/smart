package com.lingzhi.smart.module.main;

import android.support.annotation.NonNull;

import com.smart.linguoyong.data.source.Banner;
import com.smart.linguoyong.data.source.MainRepository;
import com.smart.linguoyong.data.source.RecommendBean;
import com.lingzhi.smart.module.main.MainContract;
import com.lingzhi.smart.utils.schedulers.BaseSchedulerProvider;


import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

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
        Disposable disposable = mMainRepository.getBanner().map(new Function<Banner, List<Banner.BannerEntity>>() {
            @Override
            public List<Banner.BannerEntity> apply(Banner banner) throws Exception {
                return banner.getTop();
            }
        }).subscribe(new Consumer<List<Banner.BannerEntity>>() {
            @Override
            public void accept(List<Banner.BannerEntity> bannerEntities) throws Exception {
                mView.setBannerSection(bannerEntities);
            }
        });

        Disposable disposableGetRecommendList = mMainRepository.getRecommendList().subscribe(new Consumer<List<RecommendBean>>() {
            @Override
            public void accept(List<RecommendBean> recommendBeans) throws Exception {
                mView.setRecommedSection(recommendBeans);
            }
        });


        mCompositeDisposable.add(disposable);
        mCompositeDisposable.add(disposableGetRecommendList);
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }
}
