package com.lingzhi.smart.module.search;

import android.os.SystemClock;

import com.lingzhi.smart.data.bean.AlbumBean;
import com.lingzhi.smart.data.bean.AudioBean;
import com.lingzhi.smart.data.bean.SearchResultBean;
import com.lingzhi.smart.data.source.MainRepository;
import com.lingzhi.smart.base.BasePresenter;
import com.lingzhi.smart.data.bean.SearchTagBean;
import com.lingzhi.smart.utils.Injection;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

public class SearchPresenter extends BasePresenter implements SearchContract.Presenter {
    SearchContract.View mView;
    private final MainRepository mainRepository;

    public SearchPresenter(SearchContract.View view) {
        mView = checkNotNull(view);
        mainRepository = Injection.provideMainRepository();
    }

    @Override
    public void subscribe() {
        getSearchNearlyTag();
        getSearchHotTag("sss");
    }

    @Override
    public void search(String key) {
        DisposableObserver<Integer> disposableObserver = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                SystemClock.sleep(1000);
                e.onNext(1);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer value) {
                        SearchResultBean searchResultBean = new SearchResultBean();
                        ArrayList<AlbumBean> albumBeans = new ArrayList<>();
                        albumBeans.add(new AlbumBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","八神专辑",12,1111));
                        albumBeans.add(new AlbumBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","八神专辑",12,1111));
                        albumBeans.add(new AlbumBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","八神专辑",12,1111));
                        searchResultBean.setAlbumBeanList(albumBeans);
                        ArrayList<AudioBean> audioBeans = new ArrayList<>();
                        audioBeans.add(new AudioBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","","英语听力测试",1232,"20:23"));
                        audioBeans.add(new AudioBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","","英语听力测试",1232,"20:23"));
                        audioBeans.add(new AudioBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","","英语听力测试",1232,"20:23"));
                        audioBeans.add(new AudioBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","","英语听力测试",1232,"20:23"));
                        searchResultBean.setAudioBeanList(audioBeans);
                        mView.showSearchResult(searchResultBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposableObserver);
    }

    @Override
    public void getSearchHotTag(String key) {
        DisposableObserver<Integer> disposableObserver = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                SystemClock.sleep(1000);
                e.onNext(1);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer value) {
                        ArrayList<SearchTagBean> list = new ArrayList<>();
                        list.add(new SearchTagBean("语文",90,"ssss"));
                        list.add(new SearchTagBean("英文",80,"ssss"));
                        list.add(new SearchTagBean("数学",70,"ssss"));
                        list.add(new SearchTagBean("历史",850,"ssss"));
                        list.add(new SearchTagBean("小猪",70,"ssss"));
                        list.add(new SearchTagBean("pis",60,"ssss"));
                        list.add(new SearchTagBean("八神",95,"ssss"));
                        mView.showSearchHotTag(list);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposableObserver);
    }

    @Override
    public void insertSearchHotTag(String tag) {

    }

    @Override
    public void getSearchNearlyTag() {
        Disposable disposable = mainRepository
                .getSearchNearlyTag()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<String>>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getMessage());
                    }

                    @Override
                    public void onNext(List<String> response) {
                        KLog.e("response" + response);
                        mView.showSearchNearlyTag(response);
                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void insertSearchNearlyTag(String tag) {
        Disposable disposable = mainRepository
                .insertSearchNearlyTag(tag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Boolean>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean response) {
                        KLog.e("response" + response);
                        getSearchNearlyTag();
                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void clearSearchNearlyTag() {
        Disposable disposable = mainRepository
                .clearSearchNearlyTag()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Boolean>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean response) {
                        KLog.e("response" + response);
                        if (response) mView.clearTagView();
                    }
                });
        disposables.add(disposable);
    }
}
