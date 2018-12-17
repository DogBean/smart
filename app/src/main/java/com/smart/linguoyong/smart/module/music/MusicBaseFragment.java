package com.smart.linguoyong.smart.module.music;

import com.smart.linguoyong.smart.base.RxLazyFragment;

/**
 * Created by Guoyong.Lin on 2018/12/17
 **/
public class MusicBaseFragment extends RxLazyFragment implements MusicStateListener {

    @Override
    public void onResume() {
        super.onResume();
//        ((BaseActivity) getActivity()).setMusicStateListenerListener(this);
        reloadAdapter();
    }

    @Override
    public void onStop() {
        super.onStop();
//        ((BaseActivity) getActivity()).removeMusicStateListenerListener(this);
    }

    @Override
    public void updateTrackInfo() {

    }

    @Override
    public void updateTime() {

    }

    @Override
    public void changeTheme() {

    }

    @Override
    public void reloadAdapter() {

    }
}
