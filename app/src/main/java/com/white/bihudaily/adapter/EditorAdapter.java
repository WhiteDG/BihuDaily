package com.white.bihudaily.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
public class EditorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Editor> mEditors;
    private Fragment mFragment;

    public EditorAdapter(Fragment fragment, List<Editor> editors) {
        this.mEditors = editors;
        this.mFragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.editor_item, parent, false);
        return new EditorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Editor editor = mEditors.get(position);
        if (holder instanceof EditorViewHolder) {
            final EditorViewHolder editorViewHolder = (EditorViewHolder) holder;
            ImageLoader.getInstance().displayCircularImg(mFragment, editorViewHolder.ivEditorImage, editor.getAvatar());
        }
    }

    @Override
    public int getItemCount() {
        return mEditors.size();
    }

    class EditorViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_editor_image)
        ImageView ivEditorImage;

        public EditorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
