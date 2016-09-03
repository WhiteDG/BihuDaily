package com.white.bihudaily.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.white.bihudaily.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author White
 * Date 2016/9/2
 * Time 15:27
 */
public class ThemeListAdapter extends RecyclerView.Adapter<ThemeListAdapter.ThemeViewHolder> {

    private List<String> mData;

    public ThemeListAdapter() {
        mData = new ArrayList<>();
        mData.add("首页");
        for (int i = 0; i < 10; i++) {
            mData.add("item_" + i);
        }
    }

    @Override
    public ThemeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.editor_list_item, parent, false);
        return new ThemeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ThemeViewHolder holder, int position) {
        holder.tvMenuItem.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ThemeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_editor_name)
        TextView tvMenuItem;

        public ThemeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
