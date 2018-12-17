package com.smart.linguoyong.data.source;

import java.util.List;

import io.reactivex.Flowable;

public interface TasksDataSource {


    Flowable<Banner> getBanner();

    Flowable<List<RecommendBean>> getRecommendList();
}
