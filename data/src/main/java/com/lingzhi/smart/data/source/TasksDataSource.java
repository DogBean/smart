package com.lingzhi.smart.data.source;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public interface TasksDataSource {


    Flowable<Banner> getBanner();

    Flowable<List<RecommendBean>> getRecommendList();

    /**
     * 插入最近搜索标签
     *
     * @param tag
     * @return
     */
    Observable<Boolean> insertSearchNearlyTag(String tag);
    /**
     * 最近搜索标签
     *
     * @return
     */
    Observable<List<String>> getSearchNearlyTag();
    /**
     * 清除最近搜索标签
     *
     * @return
     */
    Observable<Boolean> clearSearchNearlyTag();
}