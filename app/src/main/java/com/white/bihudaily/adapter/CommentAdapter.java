package com.white.bihudaily.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.white.bihudaily.R;
import com.white.bihudaily.base.BaseRVAdapter;
import com.white.bihudaily.bean.Comment;
import com.white.bihudaily.bean.Story;
import com.white.bihudaily.utils.ActivityUtils;
import com.white.bihudaily.utils.imageloader.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author White
 * Date 2016/8/16
 * Time 12:56
 */
public class CommentAdapter extends BaseRVAdapter<Comment> {

    // 用于Glide加载图片与Activity生命周期联动
    private Activity mActivity;

    public CommentAdapter(List<Comment> data, Activity activity) {
        super(data);
        this.mActivity = activity;
    }


    @Override
    protected RecyclerView.ViewHolder createOtherTypeViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY_VIEW) {
            View emptyItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_comment_item, parent, false);
            return new RecyclerView.ViewHolder(emptyItemView) {
                @Override
                public String toString() {
                    return super.toString();
                }
            };
        }
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder getTitleViewHolder(View itemView) {
        return new TitleViewHolder(itemView);
    }

    @Override
    protected int getTitleLayout() {
        return R.layout.comment_title_item;
    }

    @Override
    protected RecyclerView.ViewHolder getItemViewHolder(View itemView) {
        return new CommentViewHolder(itemView);
    }

    @Override
    protected int getItemLayout() {
        return R.layout.comment_item;
    }

    @Override
    protected int getItemType(int position) {
        return mData.get(position).getShowType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Comment comment = mData.get(position);
        if (holder instanceof TitleViewHolder) {
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            titleViewHolder.tvCommentCount.setText(comment.getContent());
            ActivityUtils.setVisible(comment.getContent().contains("短评"), titleViewHolder.ivShowShort);
        } else if (holder instanceof CommentViewHolder) {
            // 显示评论
            CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
            commentViewHolder.tvCommentContent.setText(comment.getContent());
            commentViewHolder.tvDate.setText(comment.getTime());
            commentViewHolder.tvLikeCount.setText(String.valueOf(comment.getLikes()));
            commentViewHolder.tvName.setText(comment.getAuthor());
            ImageLoader.getInstance().displayCircularImg(mActivity, commentViewHolder.ivAvatar, comment.getAvatar());
        }
    }

    public void addTitle(String title) {
        mData.add(new Comment(title, Story.TYPE_TITLE));
        notifyDataSetChanged();
    }

    public void addEmptyView() {
        mData.add(new Comment(TYPE_EMPTY_VIEW));
        notifyDataSetChanged();
    }


    public Comment getItem(int position) {
        return mData.get(position);
    }

    public void updateTitle(int position, String newTitle) {
        mData.get(position).setContent(newTitle);
        notifyDataSetChanged();
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_comment_count)
        TextView tvCommentCount;

        @BindView(R.id.iv_show_short)
        ImageView ivShowShort;

        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_avatar)
        ImageView ivAvatar;

        @BindView(R.id.tv_Name)
        TextView tvName;

        @BindView(R.id.tv_vote_count)
        TextView tvLikeCount;

        @BindView(R.id.tv_comment_content)
        TextView tvCommentContent;

        @BindView(R.id.tv_Date)
        TextView tvDate;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
