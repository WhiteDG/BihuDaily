package com.white.bihudaily.mystar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.white.bihudaily.R;
import com.white.bihudaily.adapter.ThemeAdapter;
import com.white.bihudaily.base.BaseWithToolbarActivity;
import com.white.bihudaily.bean.Story;
import com.white.bihudaily.data.impl.MyStarRepository;
import com.white.bihudaily.utils.ActivityUtils;
import com.white.bihudaily.other.ItemTouchListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyStarActivity extends BaseWithToolbarActivity<MyStarContract.Presenter> implements MyStarContract.View {

    @BindView(R.id.rv_comment)
    RecyclerView rvMyStar;
    ThemeAdapter mThemeAdapter;

    @Override
    protected MyStarContract.Presenter createPresenter() {
        return new MyStarPresenter(new MyStarRepository(), this);
    }

    @Override
    protected void beforeContentView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void prepareData(Intent intent) {
    }

    @Override
    protected void initView() {
        initToolbar();
        setTitle("");
        rvMyStar.setLayoutManager(new LinearLayoutManager(this));
        rvMyStar.setHasFixedSize(true);

        mThemeAdapter = new ThemeAdapter(new ArrayList<Story>(0), this);
        rvMyStar.setAdapter(mThemeAdapter);

    }

    @Override
    protected void initListener() {
        rvMyStar.addOnItemTouchListener(new ItemTouchListener(rvMyStar, new ItemTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View clickedView, int position) {
                mPresenter.openStoryDetails(mThemeAdapter.getItem(position));
            }

            @Override
            public void onItemLongClick(RecyclerView parent, View clickedView, int position) {
                // TODO: 2016/8/18 操作选项
            }
        }));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.loadStories(this);
    }

    @Override
    public void setRefreshLoadingIndicator(boolean active) {

    }

    @Override
    public void showStoryDetailsUi(Story story) {
        ActivityUtils.toThemeDeatilsActivity(this, story);
    }

    @Override
    public void showLoadingThemeError() {

    }

    @Override
    public void showNoTheme() {

    }

    @Override
    public void showStories(List<Story> stories) {
        setTitle(stories.size() + " 条收藏");
        mThemeAdapter.clearData();
        mThemeAdapter.addDataList(stories);
    }

    @Override
    public void setPresenter(MyStarContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
