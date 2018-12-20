package com.lingzhi.smart.module.albumresult;

import com.smart.linguoyong.data.bean.AlbumBean;
import com.lingzhi.smart.base.BaseView;
import com.lingzhi.smart.base.MvpPresenter;

import java.util.List;

public interface AlbumResultContract {
    interface View extends BaseView<Presenter> {
        void showLoading(String msg);
        void dissmissLoading();
        void showAlbums(List<AlbumBean> list,int page);
        void getAlbumsFail();
    }

    interface Presenter extends MvpPresenter {
       void getAlbums(String id,int page);
    }
}
