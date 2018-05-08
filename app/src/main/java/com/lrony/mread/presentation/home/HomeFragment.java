package com.lrony.mread.presentation.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.classic.common.MultipleStatusView;
import com.lrony.mread.AppRouter;
import com.lrony.mread.R;
import com.lrony.mread.data.bean.Book;
import com.lrony.mread.data.bean.Status;
import com.lrony.mread.data.db.table.BookTb;
import com.lrony.mread.mvp.MvpFragment;
import com.lrony.mread.ui.widget.CustomLoadMoreView;
import com.lrony.mread.util.DensityUtil;
import com.lrony.mread.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lrony on 18-4-23.
 */
public class HomeFragment extends MvpFragment<HomeContract.Presenter> implements HomeContract.View, Toolbar.OnMenuItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "HomeFragment";

    private List<BookTb> mBooks = new ArrayList<>();

    private Toolbar mToolbar;
    private MultipleStatusView mStatusView;
    private SwipeRefreshLayout mRefreshView;
    private RecyclerView mRecyclerView;

    private HomeBookAdapter mAdapter;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public HomeContract.Presenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 代码规范，必须调用
        getPresenter().start();

        initView(view);
        initListener();

        getPresenter().doLoadData(true);
    }

    private void initView(View view) {
        Log.d(TAG, "initView");
        mToolbar = view.findViewById(R.id.toolbar);
        mRefreshView = view.findViewById(R.id.refresh);
        mStatusView = view.findViewById(R.id.multiple_status_view);
        mStatusView.setOnRetryClickListener(mRetryClickListener);
        mRecyclerView = view.findViewById(R.id.recycler_view);

        mToolbar.setTitle(R.string.home_title);
        mToolbar.inflateMenu(R.menu.menu_home);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), ScreenUtil.isLAndscape(getContext()) ? 4 : 3));

        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new HomeBookAdapter(mBooks, getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        Log.d(TAG, "initListener");
        mToolbar.setOnMenuItemClickListener(this);

        mRefreshView.setOnRefreshListener(this);
        mRecyclerView.addOnItemTouchListener(mOnitemClickListener);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home_more:
                Log.d(TAG, "onMenuItemClick: More");
                showToast("More");
                break;
            case R.id.menu_home_search:
                Log.d(TAG, "onMenuItemClick: Search");
                showToast("Search");
                break;
        }
        return false;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        Log.d(TAG, "onSupportVisible");
        getPresenter().doLoadData(false);
    }

    private OnItemClickListener mOnitemClickListener = new OnItemClickListener() {

        @Override
        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            Log.d(TAG, "onSimpleItemClick: " + position);
            AppRouter.showBookDetailActivity(getContext(), mBooks.get(position).getId());
        }

        @Override
        public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
            super.onItemLongClick(adapter, view, position);
            Log.d(TAG, "onItemLongClick: " + position);
            getPresenter().deleteData(mBooks.get(position));
        }
    };

    private View.OnClickListener mRetryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getPresenter().doLoadData(true);
        }
    };

    @Override
    public void finishLoad(List<BookTb> bookTbs) {
        Log.d(TAG, "finishLoad: " + bookTbs.size());
        refreshData(bookTbs);
    }

    @Override
    public void finishDelete() {
        Log.d(TAG, "finishDelete");
        getPresenter().doLoadData(false);
    }

    private void refreshData(List<BookTb> bookTbs) {
        Log.d(TAG, "refreshData");
        mBooks.clear();
        for (BookTb tb : bookTbs) {
            mBooks.add(tb);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void complete() {
        super.complete();
        mRefreshView.setRefreshing(false);
        mStatusView.showContent();
        mAdapter.loadMoreComplete();
    }

    @Override
    public void loading() {
        super.loading();
        mStatusView.showLoading();
    }

    @Override
    public void empty() {
        super.empty();
        mStatusView.showEmpty();
    }

    @Override
    public void error() {
        super.error();
        mStatusView.showError();
    }

    @Override
    public void onRefresh() {
        getPresenter().doLoadData(false);
    }
}
