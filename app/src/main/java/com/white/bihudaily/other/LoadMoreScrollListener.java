package com.white.bihudaily.other;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Author White
 * Date 2016/8/19
 * Time 19:53
 */
public abstract class LoadMoreScrollListener extends RecyclerView.OnScrollListener {

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        if (newState == RecyclerView.SCROLL_STATE_IDLE && recyclerView.getAdapter().getItemCount() > 1 &&
                lastVisibleItemPosition + 1 == recyclerView.getAdapter().getItemCount()) {
            onLoadMore();
        }
    }

    public abstract void onLoadMore();
}
