package com.white.bihudaily.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.white.bihudaily.R;
import com.white.bihudaily.bean.Story;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author White
 * Date 2016/8/13
 * Time 16:42
 */
@Deprecated
public class StoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Story> mData;
    List<Integer> mReaderList;
    private Fragment mFragment;
    private View mHeaderView;

    protected static final int TYPE_ITEM = 0;
    protected static final int TYPE_TITLE = 1;
    protected static final int TYPE_FOOTER = 2;
    protected static final int TYPE_HEADER = 3;

    public StoryAdapter(List<Story> data,
                        Fragment fragment,
                        List<Integer> readerList) {
        this.mData = data;
        this.mReaderList = readerList;
        this.mFragment = fragment;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mData.clear();
        mData.add(new Story(Story.TYPE_HEADER));
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == TYPE_ITEM) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_item, parent, false);
            return new StoryViewHolder(itemView);
        } else if (viewType == TYPE_TITLE) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_item, parent, false);
            return new TitleViewHolder(itemView);
        } else if (viewType == TYPE_FOOTER) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_item, parent, false);
            return new FooterViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            return new HeaderViewHolder(mHeaderView);
        }
        return null;

    }

    @Override
    public int getItemCount() {
//        if (getHeaderView() != null) {
//            return mData.size() + 1;
//        }
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getShowType();
//        if (getHeaderView() == null) {
//            return mData.get(position).getShowType();
//        } else {
//            if (position == 0) {
//                return TYPE_HEADER;
//            } else {
//                return mData.get(position - 1).getShowType();
//            }
//        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Story story = mData.get(position);
        if (holder instanceof TitleViewHolder) {
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            titleViewHolder.tvTitleItem.setText(story.getDate());
        } else if (holder instanceof StoryViewHolder) {
            StoryViewHolder storyViewHolder = (StoryViewHolder) holder;
            if (mReaderList.contains(story.getId())) {
                storyViewHolder.tvQuestionTitle.setTextColor(mFragment.getResources().getColor(R.color.textReader));
            } else {
                storyViewHolder.tvQuestionTitle.setTextColor(mFragment.getResources().getColor(android.R.color.black));
            }
            storyViewHolder.tvQuestionTitle.setText(story.getTitle());
            Glide.with(mFragment).load(story.getImages().get(0)).fitCenter().centerCrop()
//                    .placeholder(R.drawable.image_small_default)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(storyViewHolder.ivThumbnailImage);
        } else if (holder instanceof HeaderViewHolder) {
        }


    }

    public void replaceData(List<Story> stories) {
//        mData.clear();
        mData.add(new Story("今日热闻", Story.TYPE_TITLE));
        mData.addAll(stories);
        notifyDataSetChanged();
    }

    public void addTitle(Story story) {
        mData.add(story);
        notifyDataSetChanged();
    }

    public void addStories(List<Story> stories) {
        mData.addAll(stories);
        notifyDataSetChanged();
    }

    public void addFooter() {
        mData.add(new Story(Story.TYPE_FOOTER));
        notifyDataSetChanged();
    }

    public boolean isShowFooter() {
        int lastPosition = mData.size() - 1;
        return getItemViewType(lastPosition) == TYPE_FOOTER;
    }

    public void removerFooter() {
        int lastPosition = mData.size() - 1;
        int itemViewType = getItemViewType(lastPosition);
        if (itemViewType == TYPE_FOOTER) {
            mData.remove(lastPosition);
            notifyDataSetChanged();
        }
    }

    public Story getItem(int position) {
        return mData.get(position);
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title_item)
        TextView tvTitleItem;

        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class StoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_thumbnail_image)
        ImageView ivThumbnailImage;
        @BindView(R.id.tv_question_title)
        TextView tvQuestionTitle;

        public StoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_loading)
        TextView tvLoadingItem;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
