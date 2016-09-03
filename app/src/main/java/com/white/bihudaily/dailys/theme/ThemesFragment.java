package com.white.bihudaily.dailys.theme;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.white.bihudaily.R;
import com.white.bihudaily.adapter.EditorAdapter;
import com.white.bihudaily.adapter.ThemeAdapter;
import com.white.bihudaily.base.BaseFragment;
import com.white.bihudaily.base.BaseRVAdapter;
import com.white.bihudaily.bean.Editor;
import com.white.bihudaily.bean.Story;
import com.white.bihudaily.bean.Theme;
import com.white.bihudaily.data.impl.ThemeRepository;
import com.white.bihudaily.other.ItemTouchListener;
import com.white.bihudaily.other.LoadMoreScrollListener;
import com.white.bihudaily.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class ThemesFragment extends BaseFragment<ThemeContract.Presenter> implements ThemeContract.View {
    private static final String THEME_ID = "theme_id";
    private ThemeAdapter mStoryAdapter;
    private List<Integer> readerList;
    private Theme theme;
    @BindView(R.id.rv_stories)
    RecyclerView mRvStories;

    @BindView(R.id.srl_stories)
    SwipeRefreshLayout mSrl_stories;


    private int themeId;

    private int lastStoryId;

    public ThemesFragment() {
    }


    public static ThemesFragment newInstance(int themeId) {
        ThemesFragment fragment = new ThemesFragment();
        Bundle args = new Bundle();
        args.putInt(THEME_ID, themeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            themeId = getArguments().getInt(THEME_ID);
        }
    }


    @Override
    protected ThemeContract.Presenter createPresenter() {
        return new ThemePresenter(new ThemeRepository(), this);
    }


    @Override
    public void setRefreshLoadingIndicator(final boolean active) {
//        if (getView() == null) {
//            return;
//        }
        // Make sure setRefreshing() is called after the layout is done with everything else.
        mSrl_stories.post(new Runnable() {
            @Override
            public void run() {
                mSrl_stories.setRefreshing(active);
            }
        });
    }

    @Override
    public void setMoreLoadingIndicator(boolean active) {

    }

    @Override
    public void showStoryDetailsUi(Story story) {
        ActivityUtils.toThemeDeatilsActivity(getContext(), story);
    }

    @Override
    public void showEditorListUi(Theme theme) {
        ActivityUtils.toEditorListActivity(getContext(), theme);
    }

    @Override
    public void showLoadingThemeError() {
        Snackbar.make(getView(), R.string.load_fail, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showNoTheme() {

    }

    @Override
    public void addBeforeTheme(List<Story> stories) {
        mStoryAdapter.addDataList(stories);
    }

    @Override
    public void showLoadingBeforeError() {
        showSnackBar(getView(), getResources().getString(R.string.load_fail));
    }

    @Override
    public void showTheme(Theme theme) {
        mStoryAdapter.clearData();
        // 显示顶部主题日报信息（图片，标题，版权）
        this.theme = theme;
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.viewpager_item, mRvStories, false);
        ImageView ivTop = (ImageView) headerView.findViewById(R.id.iv_top);
        TextView tvTopTitle = (TextView) headerView.findViewById(R.id.tv_top_title);
        TextView tvImgSource = (TextView) headerView.findViewById(R.id.tv_imgSource);
        tvTopTitle.setText(theme.getDescription());
        tvTopTitle.setTextSize(20);
        Glide.with(this).load(theme.getBackground()).fitCenter().centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivTop);

        // 添加属性动画
//        Animator animator = AnimatorInflater.loadAnimator(getContext(), R.animator.anim_daily_logo);
//        animator.setTarget(ivTop);
//        animator.start();

        tvImgSource.setText(theme.getImage_source());
        mStoryAdapter.setHeaderView(headerView);
        // 添加主编栏目
        List<Editor> editorList = theme.getEditors();
        if (editorList != null && editorList.size() != 0) {
            View titleView = LayoutInflater.from(getContext()).inflate(R.layout.theme_title_item, mRvStories, false);
            RecyclerView rvEditor = (RecyclerView) titleView.findViewById(R.id.rv_editor);
            rvEditor.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            rvEditor.setAdapter(new EditorAdapter(this, editorList));
            mStoryAdapter.addTitle(titleView);
        }
        // 添加数据
//        List<Story> stories = theme.getStories();
//        for (Story story : stories) {
//            if (story.getImages() == null || story.getImages().size() == 0) {
//                story.setShowType(Story.TYPE_NO_IMG_STORY);
//            }
//        }
        mStoryAdapter.addDataList(theme.getStories());
        ActivityUtils.setVisible(true, mRvStories);
    }

    @Override
    public void setLastStoryId(int lastStoryId) {
        this.lastStoryId = lastStoryId;
    }

    @Override
    public void showLoadMore(boolean active) {
        if (active) {
            mStoryAdapter.addFooter();
            mRvStories.smoothScrollToPosition(mStoryAdapter.getItemCount());
        } else {
            mStoryAdapter.removerFooter();
        }
    }

    @Override
    public void setPresenter(ThemeContract.Presenter presenter) {
        this.mPresenter = presenter;
    }


    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_dailys;
    }

    @Override
    protected void prepareData() {
//        readerList = mPresenter.getReaderList(getContext());
    }

    @Override
    protected void initView() {
        mRvStories.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvStories.setItemAnimator(new DefaultItemAnimator());
        mRvStories.setHasFixedSize(true);
        mStoryAdapter = new ThemeAdapter(new ArrayList<Story>(0), this);
        mRvStories.setAdapter(mStoryAdapter);
        mSrl_stories.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected void initListener() {
        mRvStories.addOnScrollListener(new LoadMoreScrollListener() {
            @Override
            public void onLoadMore() {
                if (mStoryAdapter.getFooterState() == BaseRVAdapter.STATE_LOADING) {
                    return;
                }
//                if (mStoryAdapter.isShowFooter()) {
//                    return;
//                }
                // 加载更多数据
                mPresenter.loadBeforeTheme(themeId, lastStoryId);
            }
        });

        // 点击事件
        mRvStories.addOnItemTouchListener(new ItemTouchListener(mRvStories, new ItemTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View clickedView, int position) {
                Story item = mStoryAdapter.getItem(position);
                if (item.getShowType() == Story.TYPE_STORY || item.getShowType() == Story.TYPE_NO_IMG_STORY) {
                    readerList = mPresenter.getReaderList(getContext());
                    if (!readerList.contains(item.getId())) {
                        mPresenter.markReader(getContext(), item.getId());
                    }
                    changeReadState(clickedView, R.id.tv_question_title);
                    mPresenter.openStoryDetails(item);
                } else if (item.getShowType() == Story.TYPE_TITLE) {
                    // 主编列表界面
                    mPresenter.openEditorList(theme);
                }
            }

            @Override
            public void onItemLongClick(RecyclerView parent, View clickedView, int position) {

            }
        }));


        // 下拉刷新
        mSrl_stories.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadTheme(themeId, getContext());
            }
        });
    }

    @Override
    protected void getData() {
        mPresenter.loadTheme(themeId, getContext());
    }
}
