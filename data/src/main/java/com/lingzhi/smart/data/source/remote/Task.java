package com.lingzhi.smart.data.source.remote;

import io.reactivex.Observable;

public interface Task <R> {
    /**
     * 唯一标识当前任务的ID
     * @return
     */
    String getTaskId();

    /**
     *
     * 启动任务.
     *
     * 注: start方法需在主线程上执行.
     *
     * @return 一个Observable. 调用者通过这个Observable获取异步任务执行结果.
     */
    Observable<R> start();
}