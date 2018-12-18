package com.smart.linguoyong.smart.module.albumresult;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smart.linguoyong.data.bean.AlbumBean;
import com.smart.linguoyong.smart.R;
import com.smart.linguoyong.smart.adapter.AlbumResultsAdapter;
import com.smart.linguoyong.smart.base.RxLazyFragment;
import com.smart.linguoyong.smart.utils.ToastUtils;
import com.smart.linguoyong.smart.view.AbsRecyclerViewAdapter;
import com.smart.linguoyong.smart.view.widget.helper.EndlessRecyclerOnScrollListener;
import com.smart.linguoyong.smart.view.widget.helper.HeaderViewRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cs
 * 影视搜索界面
 */
public class AlbumResultsFragment extends RxLazyFragment implements AlbumResultContract.View{
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_view)
    ImageView mEmptyView;
    @BindView(R.id.iv_search_loading)
    ImageView mLoadingView;

    private String content;
    private int pageNum = 1;
    private int pageSize = 9;
    private View loadMoreView;
    private AnimationDrawable mAnimationDrawable;
    private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;
    private List<AlbumBean> movies = new ArrayList<>();

    AlbumResultPresenter mPresenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_album_result, container, false);
        ButterKnife.bind(this, root);
        mPresenter = new AlbumResultPresenter(this);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        content = getArguments().getString("intentKey");
        mLoadingView.setImageResource(R.drawable.anim_search_loading);
        mAnimationDrawable = (AnimationDrawable) mLoadingView.getDrawable();
        showSearchAnim();
        lazyLoad();
    }
    private void lazyLoad(){
        initRecyclerView();
        mPresenter.getAlbums("",pageNum);
    }

    protected void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        AlbumResultsAdapter mAdapter = new AlbumResultsAdapter(mRecyclerView, movies);
        mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        mRecyclerView.setAdapter(mHeaderViewRecyclerAdapter);
        createLoadMoreView();
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int i) {
                pageNum++;
                mPresenter.getAlbums("",pageNum);
                loadMoreView.setVisibility(View.VISIBLE);
            }
        });
        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                ToastUtils.showToast(movies.get(position).getName());
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getAlbums("",pageNum);
    }
    @Override
    public void showAlbums(List<AlbumBean> list,int pageNum) {
        if (list.size() < pageSize) {
            loadMoreView.setVisibility(View.GONE);
            mHeaderViewRecyclerAdapter.removeFootView();
        }
        movies.addAll(list);
        finishTask();
    }

    public void getAlbumsFail(){
        hideSearchAnim();
        showEmptyView();
        loadMoreView.setVisibility(View.GONE);
    }
    protected void finishTask() {
        if (movies != null) {
            if (movies.size() == 0) {
                showEmptyView();
            } else {
                hideEmptyView();
            }
        }
        hideSearchAnim();
        loadMoreView.setVisibility(View.GONE);
        if (pageNum * pageSize - pageSize - 1 > 0) {
            mHeaderViewRecyclerAdapter.notifyItemRangeChanged(pageNum * pageSize - pageSize - 1,
                    pageSize);
        } else {
            mHeaderViewRecyclerAdapter.notifyDataSetChanged();
        }
    }

    private void createLoadMoreView() {
        loadMoreView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_load_more, mRecyclerView, false);
        mHeaderViewRecyclerAdapter.addFooterView(loadMoreView);
        loadMoreView.setVisibility(View.GONE);
    }

    private void showSearchAnim() {
        mLoadingView.setVisibility(View.VISIBLE);
        mAnimationDrawable.start();
    }

    private void hideSearchAnim() {
        mLoadingView.setVisibility(View.GONE);
        mAnimationDrawable.stop();
    }

    public void showEmptyView() {
        mEmptyView.setVisibility(View.VISIBLE);
    }

    public void hideEmptyView() {
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void dissmissLoading() {

    }

    @Override
    public void setPresenter(AlbumResultContract.Presenter presenter) {

    }
}
