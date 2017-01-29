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
import com.white.bihudaily.base.BaseRVAdapter;
import com.white.bihudaily.bean.Story;
import com.white.bihudaily.utils.ActivityUtils;
import com.white.bihudaily.utils.imageloader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author White
 * Date 2016/8/16
 * Time 12:56
 */
public class ThemeAdapter extends BaseRVAdapter<Story> {

    private Fragment mFragment;
    private Activity mActivity;
    private View mTitleView;

    public ThemeAdapter(List<Story> data, Fragment fragment) {
        super(data);
        this.mFragment = fragment;
    }

    public ThemeAdapter(ArrayList<Story> data, Activity activity) {
        super(data);
        this.mActivity = activity;
    }

    @Override
    protected RecyclerView.ViewHolder createOtherTypeViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NO_IMG_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_no_img_item, parent, false);
            return new NoImgStoryViewHolder(itemView);
        }
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder getTitleViewHolder(View itemView) {
        return new TitleViewHolder(mTitleView);
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
        if (holder instanceof StoryViewHolder) {
            StoryViewHolder storyViewHolder = (StoryViewHolder) holder;
            storyViewHolder.tvQuestionTitle.setTextColor(
                    storyViewHolder.tvQuestionTitle.getResources().getColor(
                            story.isRead() ? R.color.textReader : android.R.color.black));
            storyViewHolder.tvQuestionTitle.setText(story.getTitle());
            if (mFragment == null) {
                ImageLoader.getInstance().display(mActivity, storyViewHolder.ivThumbnailImage, story.getImages().get(0));
            } else {
                ImageLoader.getInstance().display(mFragment, storyViewHolder.ivThumbnailImage, story.getImages().get(0));
            }
            ActivityUtils.setVisible(story.isMultipic(), storyViewHolder.ivMultiPic);

        } else if (holder instanceof NoImgStoryViewHolder) {
            NoImgStoryViewHolder noImgStoryViewHolder = (NoImgStoryViewHolder) holder;
            noImgStoryViewHolder.tvQuestionTitle.setTextColor(
                    noImgStoryViewHolder.tvQuestionTitle.getResources().getColor(
                            story.isRead() ? R.color.textReader : android.R.color.black));
            noImgStoryViewHolder.tvQuestionTitle.setText(story.getTitle());
        }
    }


    public void addTitle(View titleView) {
        this.mTitleView = titleView;
        mData.add(new Story(Story.TYPE_TITLE));
        notifyDataSetChanged();
    }


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
        ImageView ivMultiPic;
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
