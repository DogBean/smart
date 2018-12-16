package com.smart.linguoyong.data.source;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

import static android.support.v4.util.Preconditions.checkNotNull;

public class MainRepository implements TasksDataSource {

    private static MainRepository INSTANCE = null;

    @NonNull
    private TasksDataSource mTasksRemoteDataSource;

    @NonNull
    private TasksDataSource mTasksLocalDataSource;


    // Prevent direct instantiation.
    @SuppressLint("RestrictedApi")
    private MainRepository(@NonNull TasksDataSource tasksRemoteDataSource,
                           @NonNull TasksDataSource tasksLocalDataSource) {
//        mTasksRemoteDataSource = checkNotNull(tasksRemoteDataSource);
//        mTasksLocalDataSource = checkNotNull(tasksLocalDataSource);
    }

    public static MainRepository getInstance(@NonNull TasksDataSource tasksRemoteDataSource,
                                             @NonNull TasksDataSource tasksLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new MainRepository(tasksRemoteDataSource, tasksLocalDataSource);
        }
        return INSTANCE;
    }


    public Flowable<Banner> getBanner() {
        // mock data
        Banner banner = new Banner();
        List<Banner.BannerEntity> banners = new ArrayList<>();
        banners.add(new Banner.BannerEntity("Title", null,"http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg"));
        banners.add(new Banner.BannerEntity("Title", null,"http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg"));
        banners.add(new Banner.BannerEntity("Title", null,"http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg"));
        banners.add(new Banner.BannerEntity("Title", null,"http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg"));
        banners.add(new Banner.BannerEntity("Title", null,"http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg"));
        banners.add(new Banner.BannerEntity("Title", null,"http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg"));
        banner.setTop(banners);
        return Flowable.just(banner);
    }
}
