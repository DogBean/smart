package com.lingzhi.smart.data.source;

import com.lingzhi.smart.data.bean.DatedLinkGroup;
import com.lingzhi.smart.data.bean.ResourceGroup;
import com.lingzhi.smart.data.bean.ResourceList;
import com.lingzhi.smart.data.bean.Song;
import com.lingzhi.smart.data.source.remote.Resp;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public interface TasksDataSource {


    Flowable<Resp<DatedLinkGroup>> getBanner();

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

    Observable<Boolean> insertRequisite(ResourceGroup<Song> resourceList);

    Observable<ResourceGroup> getRequisite();
}
