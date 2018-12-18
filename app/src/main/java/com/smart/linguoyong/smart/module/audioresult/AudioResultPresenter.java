package com.smart.linguoyong.smart.module.audioresult;

import android.os.SystemClock;

import com.smart.linguoyong.data.bean.AlbumBean;
import com.smart.linguoyong.data.bean.AudioBean;
import com.smart.linguoyong.data.source.MainRepository;
import com.smart.linguoyong.smart.base.BasePresenter;
import com.smart.linguoyong.smart.module.albumresult.AlbumResultContract;
import com.smart.linguoyong.smart.utils.Injection;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

public class AudioResultPresenter extends BasePresenter implements AudioResultContract.Presenter {
    AudioResultContract.View mView;
    private final MainRepository mainRepository;

    public AudioResultPresenter(AudioResultContract.View view) {
        mView = checkNotNull(view);
        mainRepository = Injection.provideMainRepository();
    }

    @Override
    public void subscribe() {
    }


    @Override
    public void getAudios(String id,int page) {
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
                        ArrayList<AudioBean> albumBeans = new ArrayList<>();
                        albumBeans.add(new AudioBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","","八神专辑",12,"12:20"));
                        albumBeans.add(new AudioBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","","八神专辑",12,"12:20"));
                        albumBeans.add(new AudioBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","","八神专辑",12,"12:20"));
                        albumBeans.add(new AudioBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","","八神专辑",12,"12:20"));
                        albumBeans.add(new AudioBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","","八神专辑",12,"12:20"));
                        albumBeans.add(new AudioBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","","八神专辑",12,"12:20"));
                        albumBeans.add(new AudioBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","","八神专辑",12,"12:20"));
                        albumBeans.add(new AudioBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","","八神专辑",12,"12:20"));
                        albumBeans.add(new AudioBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","","八神专辑",12,"12:20"));
                        albumBeans.add(new AudioBean("1","http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg","","八神专辑",12,"12:20"));
                        mView.showAudios(albumBeans,page);
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
