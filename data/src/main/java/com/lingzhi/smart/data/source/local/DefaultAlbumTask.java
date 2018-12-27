package com.lingzhi.smart.data.source.local;

import com.lingzhi.smart.data.bean.DatedLinkGroup;
import com.lingzhi.smart.data.bean.IconLink;
import com.lingzhi.smart.data.source.remote.ApiHelper;
import com.lingzhi.smart.data.source.remote.Resp;
import com.lingzhi.smart.data.source.remote.Task;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * @Description:
 * @Author Guoyong.Lin
 * @Time 2018/12/27
 */
public class DefaultAlbumTask implements Task {
    @Override
    public String getTaskId() {
        return null;
    }

    @Override
    public Observable start() {
        return null;
    }

//    @Override
//    public Flowable start() {
//        return ApiHelper.topic().flatMap(new Function<Resp<DatedLinkGroup>, Publisher<?>>() {
//            @Override
//            public Publisher<?> apply(Resp<DatedLinkGroup> datedLinkGroupResp) throws Exception {
//                if (datedLinkGroupResp == null || datedLinkGroupResp.getData() == null) {
//                    return Flowable.empty();
//                } else {
//                    DatedLinkGroup data = datedLinkGroupResp.getData();
//                    IconLink[] links = data.getLinks();
//
//                }
//            }
//        });
//    }
}
