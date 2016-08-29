package com.white.bihudaily.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.white.bihudaily.R;
import com.white.bihudaily.base.BaseRecyclerViewAdapter;
import com.white.bihudaily.bean.Story;
import com.white.bihudaily.utils.ActivityUtils;
import com.white.bihudaily.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author White
 * Date 2016/8/16
 * Time 12:56
 */
public class ThemeAdapter extends BaseRecyclerViewAdapter<Story> {

    List<Integer> mReaderList;
    private Fragment mFragment;
    private Activity mActivity;
    private View titleView;

    public ThemeAdapter(List<Story> data, Fragment fragment,
                        List<Integer> readerList) {
        super(data);
        this.mFragment = fragment;
        this.mReaderList = readerList;
    }

    public ThemeAdapter(ArrayList<Story> data, Activity activity, List<Integer> readerList) {
        super(data);
        this.mActivity = activity;
        this.mReaderList = readerList;
    }

    @Override
    protected RecyclerView.ViewHolder handlerOtherType(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NO_IMG_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_no_img_item, parent, false);
            return new NoImgStoryViewHolder(itemView);
        }
        return null;
    }

//    @Override
//    protected RecyclerView.ViewHolder getNoImgViewHolder(View itemView) {
//        return new NoImgStoryViewHolder(itemView);
//    }
//
//    @Override
//    protected int getNoImgItemLayout() {
//        return R.layout.story_no_img_item;
//    }

    @Override
    protected RecyclerView.ViewHolder getTitleViewHolder(View itemView) {
        return new TitleViewHolder(titleView);
    }

    @Override
    protected int getTitleLayout() {
        return R.layout.theme_title_item;
    }

    @Override
    protected RecyclerView.ViewHolder getItemViewHolder(View itemView) {
        return new StoryViewHolder(itemView);
    }

    @Override
    protected int getItemLayout() {
        return R.layout.story_item;
    }

    @Override
    protected int getItemType(int position) {
        return mData.get(position).getShowType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Story story = mData.get(position);
        if (holder instanceof TitleViewHolder) {
//            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
//            titleViewHolder.tvTitleItem.setText(story.getDate());
        } else if (holder instanceof StoryViewHolder) {
            StoryViewHolder storyViewHolder = (StoryViewHolder) holder;
            setReader(storyViewHolder.tvQuestionTitle, story.getId());
            storyViewHolder.tvQuestionTitle.setText(story.getTitle());
            if (story.getImages() != null && story.getImages().size() != 0) {
                if (mFragment == null) {
                    ImageLoader.display(mActivity, storyViewHolder.ivThumbnailImage, story.getImages().get(0));
                } else {
                    ImageLoader.display(mFragment, storyViewHolder.ivThumbnailImage, story.getImages().get(0));
                }
                ActivityUtils.setVisible(story.isMultipic(), storyViewHolder.ivMultipic);
            }
        } else if (holder instanceof NoImgStoryViewHolder) {
            NoImgStoryViewHolder noImgStoryViewHolder = (NoImgStoryViewHolder) holder;

            setReader(noImgStoryViewHolder.tvQuestionTitle, story.getId());
            noImgStoryViewHolder.tvQuestionTitle.setText(story.getTitle());

        }
    }

    private void setReader(TextView tvQuestionTitle, int storyId) {
        int color;
        if (mReaderList.contains(storyId)) {
            if (mFragment == null) {
                color = mActivity.getResources().getColor(R.color.textReader);
            } else {
                color = mFragment.getResources().getColor(R.color.textReader);
            }
            tvQuestionTitle.setTextColor(color);
        } else {
            if (mFragment == null) {
                color = mActivity.getResources().getColor(android.R.color.black);
            } else {
                color = mFragment.getResources().getColor(android.R.color.black);
            }
            tvQuestionTitle.setTextColor(color);
        }
    }

//    public void replaceData(List<Story> stories) {
//        mData.addAll(stories);
//        notifyDataSetChanged();
//    }

    public void addTitle(View titleView) {
        this.titleView = titleView;
        mData.add(new Story(Story.TYPE_TITLE));
        notifyDataSetChanged();
    }

//    public void addStories(List<Story> stories) {
//        mData.addAll(stories);
//        notifyDataSetChanged();
//    }

//    public void addFooter() {
//        mData.add(new Story(Story.TYPE_FOOTER));
//        notifyDataSetChanged();
//    }
//
//    public boolean isShowFooter() {
//        int lastPosition = mData.size() - 1;
//        return getItemViewType(lastPosition) == TYPE_FOOTER;
//    }
//
//    public void removerFooter() {
//        int lastPosition = mData.size() - 1;
//        int itemViewType = getItemViewType(lastPosition);
//        if (itemViewType == TYPE_FOOTER) {
//            mData.remove(lastPosition);
//            notifyDataSetChanged();
//        }
//    }

//    public Story getItem(int position) {
//        return mData.get(position);
//    }

//    public void clearData() {
//        mData.clear();
//        notifyDataSetChanged();
//    }


    class TitleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rv_editor)
        RecyclerView rvEditor;

        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class StoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_thumbnail_image)
        ImageView ivThumbnailImage;
        @BindView(R.id.iv_multipic)
        ImageView ivMultipic;
        @BindView(R.id.tv_question_title)
        TextView tvQuestionTitle;

        public StoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class NoImgStoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_question_title)
        TextView tvQuestionTitle;

        public NoImgStoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
