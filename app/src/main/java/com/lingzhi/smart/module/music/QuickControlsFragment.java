package com.lingzhi.smart.module.music;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lingzhi.smart.R;
import com.lingzhi.smart.utils.Navigator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by Guoyong.Lin on 2018/12/17
 **/
public class QuickControlsFragment extends MusicBaseFragment {

    @BindView(R.id.ll_play_view)
    LinearLayout viewPlay;
    @BindView(R.id.image_play_control)
    ImageView viewPlayControl;
    Unbinder unbinder;

    public static QuickControlsFragment newInstance() {
        return new QuickControlsFragment();
    }

    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quick_controls, container, false);
        mContext = getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ll_play_view)
    public void startToMusicPlay() {
        Navigator.navigateToMusicPlay(getContext());
    }

}
