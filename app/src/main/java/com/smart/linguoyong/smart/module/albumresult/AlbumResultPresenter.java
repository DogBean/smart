package com.smart.linguoyong.smart.module.albumresult;

import android.os.SystemClock;

import com.smart.linguoyong.data.bean.AlbumBean;
import com.smart.linguoyong.data.source.MainRepository;
import com.smart.linguoyong.smart.base.BasePresenter;
import com.smart.linguoyong.smart.base.RxBus;
import com.smart.linguoyong.smart.event.AlbumTagEvent;
import com.smart.linguoyong.smart.utils.Injection;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import rx.functions.Action1;

import static com.google.common.base.Preconditions.checkNotNull;

public class AlbumResultPresenter extends BasePresenter implements AlbumResultContract.Presenter {
    AlbumResultContract.View mView;
    private final MainRepository mainRepository;

    public AlbumResultPresenter(AlbumResultContract.View view) {
        mView = checkNotNull(view);
        mainRepository = Injection.provideMainRepository();
    }

    @Override
    public void subscribe() {

    }


    @Override
    public void getAlbums(String id,int page) {
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
                        ArrayList<AlbumBean> albumBeans = new ArrayList<>();
                        albumBeans.add(new AlbumBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","八神专辑",12,1111));
                        albumBeans.add(new AlbumBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","八神专辑",12,1111));
                        albumBeans.add(new AlbumBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","八神专辑",12,1111));
                        albumBeans.add(new AlbumBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","八神专辑",12,1111));
                        albumBeans.add(new AlbumBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","八神专辑",12,1111));
                        albumBeans.add(new AlbumBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","八神专辑",12,1111));
                        albumBeans.add(new AlbumBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","八神专辑",12,1111));
                        albumBeans.add(new AlbumBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","八神专辑",12,1111));
                        albumBeans.add(new AlbumBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","八神专辑",12,1111));
                        mView.showAlbums(albumBeans,page);
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
}
