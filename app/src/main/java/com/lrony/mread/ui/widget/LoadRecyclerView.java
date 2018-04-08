package com.lrony.mread.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Lrony on 18-4-8.
 */
public class LoadRecyclerView extends RecyclerView {

    public LoadRecyclerView(Context context) {
        super(context);
    }

    public LoadRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private onLoadMore mOnLoadMoreListener;

    public interface onLoadMore {
        void onLoadMore();
    }

    public void setonLoadMoreListener(onLoadMore onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public void onScrollStateChanged(int state) {
        /*SCROLL_STATE_IDLE表示当前并不处于滑动状态
        SCROLL_STATE_DRAGGING表示当前RecyclerView处于滑动状态（手指在屏幕上）
        SCROLL_STATE_SETTLING表示当前RecyclerView处于滑动状态，（手已经离开屏幕）*/
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            LayoutManager layoutManager = getLayoutManager();
            int lastVisiblePosition = 0;
            if (layoutManager instanceof GridLayoutManager) {
                lastVisiblePosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                // TODO findMax?
                /*int into[] = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisiblePosition = findMax(into);*/
            } else {
                lastVisiblePosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }

            if (layoutManager.getChildCount() > 0             //当当前显示的item数量>0
                    && lastVisiblePosition >= layoutManager.getItemCount() - 2           //当当前屏幕最后一个加载项位置>=所有item的数量
                    && layoutManager.getItemCount() > layoutManager.getChildCount()) { // 当当前总Item数大于可见Item数
                Log.d("LoadMoreRecyclerView", "run onLoadMore");
                if (mOnLoadMoreListener != null) {
                    mOnLoadMoreListener.onLoadMore();
                }
            }
        }
    }
}
