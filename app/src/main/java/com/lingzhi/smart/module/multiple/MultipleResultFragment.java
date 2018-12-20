package com.lingzhi.smart.module.multiple;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smart.linguoyong.data.bean.SearchResultBean;
import com.lingzhi.smart.R;
import com.lingzhi.smart.base.RxLazyFragment;
import com.lingzhi.smart.view.section.SearchAudioResultSection;
import com.lingzhi.smart.view.section.SearchAlbumResultSection;
import com.lingzhi.smart.view.sectioned.SectionedRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

public class MultipleResultFragment extends RxLazyFragment implements MultipleResultContract.View {

    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    private SectionedRecyclerViewAdapter mSectionedRecyclerViewAdapter;


    @Override
    public void onResume() {
        super.onResume();
        if(mPresenter != null){
            mPresenter.subscribe();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mSectionedRecyclerViewAdapter.removeAllSections();
        mSectionedRecyclerViewAdapter.notifyDataSetChanged();
        if(mPresenter!=null){
            mPresenter.unsubscribe();
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_multiple_result, container, false);
        ButterKnife.bind(this, root);
        initRecyclerView();
        return root;
    }

    protected void initRecyclerView() {
        mSectionedRecyclerViewAdapter = new SectionedRecyclerViewAdapter();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mSectionedRecyclerViewAdapter);
    }

    @Override
    public void setPresenter(MultipleResultContract.Presenter presenter) {
    }

    public void setResult(SearchResultBean resultSection) {
        mSectionedRecyclerViewAdapter.removeAllSections();
        mSectionedRecyclerViewAdapter.addSection(new SearchAlbumResultSection(getActivity(),0,resultSection));
        mSectionedRecyclerViewAdapter.addSection(new SearchAudioResultSection(getActivity(),0,resultSection));
        mSectionedRecyclerViewAdapter.notifyDataSetChanged();
    }

}