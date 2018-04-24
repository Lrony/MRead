package com.lrony.mread.ui.help;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Lrony on 18-4-24.
 * 监听RecyclerView滑动到底部
 */
public abstract class OnLoadMoreScrollListener extends RecyclerView.OnScrollListener {

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE //滑动状态为停顿状态
                && recyclerView.getLayoutManager().getChildCount() > 0
                && canTriggerLoadMore(recyclerView)) {
            onLoadMore(recyclerView);
        }
    }

    private boolean canTriggerLoadMore(RecyclerView recyclerView) {
        View lastChild = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
        int position = recyclerView.getChildLayoutPosition(lastChild);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int totalItemCount = layoutManager.getItemCount();
        return totalItemCount - 1 == position;
    }

    public abstract void onLoadMore(RecyclerView var1);
}
