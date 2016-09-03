package com.white.bihudaily.comment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.white.bihudaily.R;
import com.white.bihudaily.adapter.CommentAdapter;
import com.white.bihudaily.app.Constant;
import com.white.bihudaily.base.BaseRVAdapter;
import com.white.bihudaily.base.BaseWithToolbarActivity;
import com.white.bihudaily.bean.AdapterBean;
import com.white.bihudaily.bean.Comment;
import com.white.bihudaily.bean.Comments;
import com.white.bihudaily.data.impl.CommentRepository;
import com.white.bihudaily.other.DividerItemDecoration;
import com.white.bihudaily.other.ItemTouchListener;
import com.white.bihudaily.other.LoadMoreScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CommentActivity extends BaseWithToolbarActivity<CommentContract.Presenter> implements CommentContract.View {

    @BindView(R.id.rv_comment)
    RecyclerView rvComment;

    private CommentAdapter mCommentAdapter;
    private ItemTouchListener itemTouchListener;

    private int storyId;
    private int commentCount;
    private int longCommentCount;
    private int shortCommentCount;

    private int lastCommentId;

    private boolean noMore;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_comment:

                break;
        }
        return super.onOptionsItemSelected(item);
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
        storyId = intent.getIntExtra(Constant.STORY_ID, 0);
        commentCount = intent.getIntExtra(Constant.COMMENT_COUNT, 0);
        shortCommentCount = intent.getIntExtra(Constant.SHORT_COMMENT_COUNT, 0);
        longCommentCount = intent.getIntExtra(Constant.LONG_COMMENT_COUNT, 0);
    }

    @Override
    protected void initView() {
        initToolbar(commentCount + getResources().getString(R.string.comment_title));
        rvComment.setLayoutManager(new LinearLayoutManager(this));
        rvComment.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        rvComment.setHasFixedSize(true);
        mCommentAdapter = new CommentAdapter(new ArrayList<Comment>(0), this);
        rvComment.setAdapter(mCommentAdapter);

    }

    @Override
    protected void initListener() {
        itemTouchListener = new ItemTouchListener(rvComment, new ItemTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View clickedView, int position) {
                Comment showShortComment = mCommentAdapter.getItem(position);
                if (showShortComment.getShowType() == AdapterBean.TYPE_TITLE && showShortComment.getContent().contains("短评")) {
                    // 展开短评
                    mPresenter.loadShortComment(storyId);
                }
            }

            @Override
            public void onItemLongClick(RecyclerView parent, View clickedView, int position) {

            }
        });
        rvComment.addOnItemTouchListener(itemTouchListener);
        rvComment.addOnScrollListener(new LoadMoreScrollListener() {
            @Override
            public void onLoadMore() {
                if (noMore) {
                    showToast(R.string.no_more_comment);
                    return;
                }
                // 没有加载过短评或处于加载状态直接返回
                if (lastCommentId == 0 ||
                        mCommentAdapter.getFooterState() == BaseRVAdapter.STATE_LOADING) {
                    return;
                }
                // 加载更多评论
                mPresenter.loadBeforeShortComment(storyId, lastCommentId);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter.loadLongComment(storyId);
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            showProgressDialog(getString(R.string.loading));
        } else {
            dismissProgressDialog();
        }
    }

    @Override
    public void showLoadFail() {
        showToast(R.string.load_fail);
    }

    @Override
    public void showLongComment(Comments comments) {
        mCommentAdapter.addTitle(comments.getComments().size() + getString(R.string.long_comment_title));// 添加长评论数展示
        if (comments.getComments() == null || comments.getComments().size() == 0) {
            mCommentAdapter.addEmptyView(); //深度长评虚位以待
        } else {
            mCommentAdapter.addDataList(comments.getComments()); // 填加长评论
        }
        mCommentAdapter.addTitle(shortCommentCount + getString(R.string.short_comment_title));// 添加短评论数展示


    }

    @Override
    public void showShortComment(Comments comments) {
        int itemCount = mCommentAdapter.getItemCount();
        rvComment.smoothScrollToPosition(itemCount);
        mCommentAdapter.addDataList(comments.getComments());
        rvComment.removeOnItemTouchListener(itemTouchListener);
    }

    @Override
    public void setLastCommentId(int lastCommentId) {
        this.lastCommentId = lastCommentId;
    }

    @Override
    public void showLoadMore(boolean active) {
        if (active) {
            mCommentAdapter.addFooter();
            rvComment.smoothScrollToPosition(mCommentAdapter.getItemCount());
        } else {
            mCommentAdapter.removerFooter();
        }
    }

    @Override
    public void showBeforeComment(List<Comment> beforeCommentList) {
        mCommentAdapter.addDataList(beforeCommentList);
    }

    @Override
    public void showNoMoreData() {
        noMore = true;
        showToast(getString(R.string.no_more_comment));
    }


    @Override
    protected CommentContract.Presenter createPresenter() {
        return new CommentPresenter(new CommentRepository(), this);
    }

}
