package com.white.bihudaily.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.white.bihudaily.R;
import com.white.bihudaily.bean.Editor;
import com.white.bihudaily.utils.imageloader.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author White
 * Date 2016/8/16
 * Time 21:31
 */
public class EditorListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Editor> mEditors;
    private Activity mContext;

    public EditorListAdapter(Activity context, List<Editor> editors) {
        this.mEditors = editors;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.editor_list_item, parent, false);
        return new EditorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Editor editor = mEditors.get(position);
        if (holder instanceof EditorViewHolder) {
            final EditorViewHolder editorViewHolder = (EditorViewHolder) holder;
            ImageLoader.getInstance().displayCircularImg(mContext, editorViewHolder.ivEditorImage, editor.getAvatar());
            editorViewHolder.tvEditorBio.setText(editor.getBio());
            editorViewHolder.tvEditorName.setText(editor.getName());
        }
    }

    @Override
    public int getItemCount() {
        return mEditors.size();
    }

    class EditorViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_editor_avatar)
        ImageView ivEditorImage;

        @BindView(R.id.tv_editor_name)
        TextView tvEditorName;

        @BindView(R.id.tv_editor_bio)
        TextView tvEditorBio;

        public EditorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
