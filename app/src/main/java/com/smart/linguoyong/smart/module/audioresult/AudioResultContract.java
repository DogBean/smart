package com.smart.linguoyong.smart.module.audioresult;

import com.smart.linguoyong.data.bean.AlbumBean;
import com.smart.linguoyong.data.bean.AudioBean;
import com.smart.linguoyong.smart.base.BaseView;
import com.smart.linguoyong.smart.base.MvpPresenter;

import java.util.List;

public interface AudioResultContract {
    interface View extends BaseView<Presenter> {
        void showLoading(String msg);
        void dissmissLoading();
        void showAudios(List<AudioBean> list, int page);
        void getAudiosFail();
    }

    interface Presenter extends MvpPresenter {
       void getAudios(String id, int page);
    }
}
