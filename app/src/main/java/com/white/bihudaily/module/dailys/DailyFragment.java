package com.white.bihudaily.module.dailys;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.white.bihudaily.R;
import com.white.bihudaily.adapter.StoriesAdapter;
import com.white.bihudaily.app.Constant;
import com.white.bihudaily.base.BaseFragment;
import com.white.bihudaily.base.BaseRVAdapter;
import com.white.bihudaily.bean.Latest;
import com.white.bihudaily.bean.Story;
import com.white.bihudaily.bean.TopStory;
import com.white.bihudaily.data.impl.DailyRepository;
import com.white.bihudaily.other.ItemTouchListener;
import com.white.bihudaily.other.LoadMoreScrollListener;
import com.white.bihudaily.other.RollViewPager;
import com.white.bihudaily.utils.ActivityUtils;
import com.white.bihudaily.utils.CommonUtil;
import com.white.bihudaily.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class DailyFragment extends BaseFragment<DailyContract.Presenter> implements DailyContract.View {

    private StoriesAdapter mStoryAdapter;
    private List<Integer> readerList;

    @BindView(R.id.rv_stories)
    RecyclerView mRvStories;

    @BindView(R.id.srl_stories)
    SwipeRefreshLayout mSrl_stories;

    private String currentDate;


    public DailyFragment() {
    }


    public static DailyFragment newInstance() {
        return new DailyFragment();
    }


    @Override
    protected DailyContract.Presenter createPresenter() {
        return new DailyPresenter(new DailyRepository(), this);
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
        mStoryAdapter = new StoriesAdapter(new ArrayList<Story>(0), this);
        mRvStories.setAdapter(mStoryAdapter);
        mSrl_stories.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected void initListener() {
        // 上拉加载。。。
        mRvStories.addOnScrollListener(new LoadMoreScrollListener() {
            @Override
            public void onLoadMore() {
                if (mStoryAdapter.getFooterState() == BaseRVAdapter.STATE_LOADING) {
                    return;
                }
                // 加载前一天的数据
                mPresenter.loadBefore(currentDate);
            }
        });


        // 点击事件
        mRvStories.addOnItemTouchListener(new ItemTouchListener(mRvStories, new ItemTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View clickedView, int position) {
                Story item = mStoryAdapter.getItem(position);
                if (item.getShowType() == Story.TYPE_STORY) {
                    readerList = mPresenter.getReaderList(getContext());
                    if (!readerList.contains(item.getId())) {
                        mPresenter.markReader(getContext(), item.getId());
                    }
                    changeReadState(clickedView, R.id.tv_question_title);
                    mPresenter.openStoryDetails(item);
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
                mPresenter.loadLatest(getContext(), false);
            }
        });
    }

    @Override
    protected void getData() {
        mPresenter.loadLatest(getContext(), (boolean) SPUtils.get(getContext(), Constant.KEY_HAS_CACHE, false));
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
    public void showTopStories(final List<TopStory> topStories) {
        View headerView = initHeaderView(topStories);
        mStoryAdapter.setHeaderView(headerView);
    }

    /**
     * 获取顶部轮播viewpager
     *
     * @param topStories 置顶数据
     * @return
     */
    private View initHeaderView(final List<TopStory> topStories) {
        if (topStories != null && topStories.size() > 0) {
            View headerView = LayoutInflater.from(getContext()).inflate(R.layout.header_viewpager, mRvStories, false);
            FrameLayout flTop = (FrameLayout) headerView.findViewById(R.id.fl_top); // 放ViewPager的framelayout
            LinearLayout LlDots = (LinearLayout) headerView.findViewById(R.id.ll_dots);// 放小圆点的linerlayout
            // 初始化viewpager，小圆点
            RollViewPager rollViewPager = new RollViewPager(getContext(), initDots(LlDots, topStories.size()),
                    R.drawable.point_focured,
                    R.drawable.point_nomal, new RollViewPager.OnPagerClickCallback() {
                @Override
                public void onPagerClick(int position) {
                    TopStory topStory = topStories.get(position);
                    Story story = new Story(topStory.getId(), topStory.getTitle(), topStory.getImage(), null);
                    ActivityUtils.toStoryDetailActivity(getContext(), story);
                }
            });
            rollViewPager.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    CommonUtil.dip2px(getContext(), 240)));

            rollViewPager.setTopStories(topStories);
            rollViewPager.startRoll();
            // 将viewpager放进去
            flTop.removeAllViews();
            flTop.addView(rollViewPager);
            return headerView;
        }
        return null;
    }

    /**
     * 初始化顶部轮播小圆点
     *
     * @param LlDot 承载布局
     * @param size  圆点数
     * @return
     */
    private ArrayList<View> initDots(LinearLayout LlDot, int size) {
        ArrayList<View> dotList = new ArrayList<>();
        LlDot.removeAllViews();
        for (int i = 0; i < size; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    CommonUtil.dip2px(getContext(), 6), CommonUtil.dip2px(getContext(), 6));
            params.setMargins(5, 0, 5, 0);
            View m = new View(getContext());
            if (i == 0) {
                m.setBackgroundResource(R.drawable.point_focured);
            } else {
                m.setBackgroundResource(R.drawable.point_nomal);
            }
            m.setLayoutParams(params);
            LlDot.addView(m);
            dotList.add(m);
        }
        return dotList;
    }

    @Override
    public void showStories(List<Story> stories) {
        mStoryAdapter.addTitle(getResources().getString(R.string.today_news));
        mStoryAdapter.addDataList(stories);
        ActivityUtils.setVisible(true, mRvStories);
    }

    @Override
    public void showLatest(Latest latest) {
        mStoryAdapter.clearData();
        if (latest.getTop_stories() != null && latest.getTop_stories().size() != 0) {
            showTopStories(latest.getTop_stories());
        }
        showStories(latest.getStories());
    }

    @Override
    public void setCurrentDate(String date) {
        this.currentDate = date;
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
    public void showStoryDetailsUi(Story story) {
        ActivityUtils.toStoryDetailActivity(getContext(), story);
    }

    @Override
    public void showLoadingLatestError() {
        showSnackBar(getView(), R.string.load_fail);
    }

    @Override
    public void showLoadingBeforeError() {
        showSnackBar(getView(), R.string.load_fail);
    }


    @Override
    public void showNoStories() {

    }

    @Override
    public void addBefore(List<Story> stories) {
        mStoryAdapter.addTitle(CommonUtil.getShowDate(currentDate));
        mStoryAdapter.addDataList(stories);
    }

    @Override
    public void setPresenter(DailyContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
