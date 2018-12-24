package com.lingzhi.smart.module.requisite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.lingzhi.smart.R;
import com.lingzhi.smart.app.SmartApplication;
import com.lingzhi.smart.data.bean.ResourceList;
import com.lingzhi.smart.module.music.MusicPlayerActivity;
import com.lingzhi.smart.module.playList.PlayListActivity;
import com.lingzhi.smart.utils.CommonUtils;
import com.lingzhi.smart.utils.Injection;
import com.lingzhi.smart.view.section.RequisiteSession;
import com.lingzhi.smart.view.sectioned.SectionedRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RequisiteActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {
    @BindView(R.id.empty)
    ImageView empty;
    @BindView(R.id.recyclerview_container)
    RelativeLayout recyclerviewContainer;
    @BindView(R.id.album_art)
    ImageView albumArt;
    @BindView(R.id.overlay)
    View overlay;
    @BindView(R.id.playlist_art)
    ImageView playlistArt;
    @BindView(R.id.headerview)
    FrameLayout headerview;
    private ActionBar actionBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private int mActionBarSize;
    private int mStatusSize;
    @BindView(R.id.recyclerview)
    ObservableRecyclerView recyclerView;
    private SectionedRecyclerViewAdapter mSectionedRecyclerViewAdapter;
    static ResourceList resourceList = new ResourceList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisite);
        ButterKnife.bind(this);

        mActionBarSize = CommonUtils.getActionBarHeight(this);
        mStatusSize = CommonUtils.getStatusHeight(this);
        setupToolbar();
        setList();


    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.actionbar_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("今日必听");
        toolbar.setPadding(0, mStatusSize, 0, 0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setList() {
        recyclerView.setScrollViewCallbacks(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSectionedRecyclerViewAdapter = new SectionedRecyclerViewAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mSectionedRecyclerViewAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Injection.provideMainRepository().getRequisite().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResourceList>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResourceList resourceList) {
                mSectionedRecyclerViewAdapter.addSection(new RequisiteSession(RequisiteActivity.this, resourceList));
                mSectionedRecyclerViewAdapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(SmartApplication.TAG, "onError: ", e);
            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, RequisiteActivity.class);
        return intent;
    }
}
