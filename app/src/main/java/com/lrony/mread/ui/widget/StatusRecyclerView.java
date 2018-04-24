package com.lrony.mread.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.classic.common.MultipleStatusView;
import com.lrony.mread.R;

/**
 * Created by Lrony on 18-4-24.
 */
public class StatusRecyclerView extends RecyclerView {

    private MultipleStatusView mStatusView;
    private SwipeRefreshLayout mRefreshView;
    private RecyclerView mRecyclerView;

    public StatusRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public StatusRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.recycler_status_view, this);
        mStatusView = findViewById(R.id.multiple_status_view);
        mRefreshView = findViewById(R.id.refresh_view);
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    public MultipleStatusView getmStatusView() {
        return mStatusView;
    }

    public SwipeRefreshLayout getmRefreshView() {
        return mRefreshView;
    }

    public void showLoading() {
        mStatusView.showLoading();
    }

    public void showContent() {
        mStatusView.showContent();
    }

    public void showEmpty() {
        mStatusView.showEmpty();
    }

    public void showError() {
        mStatusView.showError();
    }

    public void showNoNetWork() {
        mStatusView.showNoNetwork();
    }
}
