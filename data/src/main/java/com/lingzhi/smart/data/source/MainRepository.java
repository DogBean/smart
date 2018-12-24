package com.lingzhi.smart.data.source;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lingzhi.smart.data.bean.DatedLinkGroup;
import com.lingzhi.smart.data.source.remote.ApiHelper;
import com.lingzhi.smart.data.source.remote.Resp;
import com.lingzhi.smart.data.utils.SPUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Function;


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


    public Flowable<Resp<DatedLinkGroup>> getBanner() {
        return ApiHelper.banner();
    }


    public Flowable<List<RecommendBean>> getRecommendList() {
        // mock data
        List<RecommendBean> list = new ArrayList<>();
        RecommendBean bean = new RecommendBean();
        bean.setCover("http://i1.hdslb.com/bfs/archive/bd7586e4da669ae33f35c77ee32031e79fc9cbf2.jpg");
        List<RecommendBean.PlayBean> objects = new ArrayList<>();
        objects.add(new RecommendBean.PlayBean("三字经", 20));
        objects.add(new RecommendBean.PlayBean("三字经", 20));
        objects.add(new RecommendBean.PlayBean("三字经", 20));
        objects.add(new RecommendBean.PlayBean("三字经", 20));
        bean.setPlayBeans(objects);
        list.add(bean);
        return Flowable.just(list);
    }


    @Override
    public Observable<Boolean> insertSearchNearlyTag(String tag) {
        String strJson = SPUtils.getString("nearlyTag");
        Gson gson = new Gson();
        List<String> datalist = gson.fromJson(strJson, new TypeToken<List<String>>() {
        }.getType());
        if (datalist == null) datalist = new ArrayList<>();
        if (!datalist.contains(tag)) {
            if (datalist.size() < 8) {
                datalist.add(tag);
            } else {
                datalist.remove(datalist.size() - 1);
                datalist.add(0, tag);
            }
        } else {
            datalist.remove(tag);
            datalist.add(0, tag);
        }
        SPUtils.putString("nearlyTag", gson.toJson(datalist));
        return Observable.just(true);
    }

    @Override
    public Observable<List<String>> getSearchNearlyTag() {
        String strJson = SPUtils.getString("nearlyTag");
        if (!TextUtils.isEmpty(strJson)) {
            Gson gson = new Gson();
            List<String> datalist = gson.fromJson(strJson, new TypeToken<List<String>>() {
            }.getType());
            return Observable.just(datalist);
        } else {
            return Observable.empty();
        }
    }

    @Override
    public Observable<Boolean> clearSearchNearlyTag() {
        SPUtils.putString("nearlyTag", "");
        return Observable.just(true);
    }
}
