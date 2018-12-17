package com.smart.linguoyong.smart.module.sort;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smart.linguoyong.smart.R;
import com.smart.linguoyong.smart.base.RxLazyFragment;

import butterknife.ButterKnife;

/**
 * @Description :
 * @Author oh
 * @Time 2018/12/17
 */
public class SortFragment extends RxLazyFragment {


    public static SortFragment newInstance(String order) {
        SortFragment mFragment = new SortFragment();
        Bundle bundle = new Bundle();
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sort, container, false);
        ButterKnife.bind(this, root);
        return root;
    }
}
