package com.white.bihudaily.editor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.white.bihudaily.BasePresenter;
import com.white.bihudaily.R;
import com.white.bihudaily.adapter.EditorListAdapter;
import com.white.bihudaily.app.Constant;
import com.white.bihudaily.base.BaseWithToolbarActivity;
import com.white.bihudaily.bean.Editor;
import com.white.bihudaily.bean.Theme;
import com.white.bihudaily.other.DividerItemDecoration;
import com.white.bihudaily.other.ItemTouchListener;
import com.white.bihudaily.utils.ActivityUtils;

import java.util.List;

import butterknife.BindView;

public class EditorActivity extends BaseWithToolbarActivity {

    @BindView(R.id.rv_editor_list)
    RecyclerView rvEditorList;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private List<Editor> mEditors;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void beforeContentView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_editor;
    }

    @Override
    protected void prepareData(Intent intent) {
        Theme theme = (Theme) intent.getSerializableExtra(Constant.THEME);
        mEditors = theme.getEditors();
    }

    @Override
    protected void initView() {
        initToolbar(R.string.editor_list_title);
        rvEditorList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvEditorList.setItemAnimator(new DefaultItemAnimator());
        rvEditorList.setHasFixedSize(true);
        rvEditorList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        rvEditorList.setAdapter(new EditorListAdapter(this, mEditors));
    }

    @Override
    protected void initListener() {
        rvEditorList.addOnItemTouchListener(new ItemTouchListener(rvEditorList, new ItemTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View clickedView, int position) {
                ActivityUtils.toEditorInfoActivity(EditorActivity.this, mEditors.get(position));
            }

            @Override
            public void onItemLongClick(RecyclerView parent, View clickedView, int position) {

            }
        }));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
