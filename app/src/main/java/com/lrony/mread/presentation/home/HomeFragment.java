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
import com.lrony.mread.R;
import com.lrony.mread.data.bean.Status;
import com.lrony.mread.mvp.MvpFragment;
import com.lrony.mread.util.DensityUtil;
import com.lrony.mread.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lrony on 18-4-23.
 */
public class HomeFragment extends MvpFragment<HomeContract.Presenter> implements HomeContract.View
        , SwipeRefreshLayout.OnRefreshListener, Toolbar.OnMenuItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String TAG = "HomeFragment";

    private Toolbar mToolbar;
    private MultipleStatusView mStatusView;
    private SwipeRefreshLayout mRefreshView;
    private RecyclerView mRecyclerView;

    private HomeBookAdapter mAdapter;

    // TestData
    List<Status> mTestData = new ArrayList<>();

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
    }

    private void initView(View view) {
        Log.d(TAG, "initView");
        mToolbar = view.findViewById(R.id.home_toolbar);
        mStatusView = view.findViewById(R.id.multiple_status_view);
        mStatusView.setOnRetryClickListener(mRetryClickListener);
        mRefreshView = view.findViewById(R.id.refresh_view);
        mRecyclerView = view.findViewById(R.id.recycler_view);

        mToolbar.setTitle(R.string.home_title);
        mToolbar.inflateMenu(R.menu.menu_home);

        mRefreshView.setColorSchemeResources(R.color.colorAccent);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), ScreenUtil.isLAndscape(getContext()) ? 4 : 3));
        mRecyclerView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        int i = getResources().getDisplayMetrics().widthPixels;
        int y = DensityUtil.dp2px(getContext(), 90);
        int p = DensityUtil.dp2px(getContext(), 15);
        int padding = (3 * y + 2 * 3 * p + 2 * p - i) / (2 * 3);
        mRecyclerView.setPadding(
                padding,
                DensityUtil.dp2px(getContext(), 8),
                padding,
                DensityUtil.dp2px(getContext(), 8)
        );

        initAdapter();
    }

    private void initAdapter() {

        for (int i = 0; i < 20; i++) {
            mTestData.add(new Status(i + ""));
        }
        mAdapter = new HomeBookAdapter(mTestData, getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        Log.d(TAG, "initListener");
        mRefreshView.setOnRefreshListener(this);
        mToolbar.setOnMenuItemClickListener(this);

        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.addOnItemTouchListener(mOnitemClickListener);
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh");
        mRefreshView.setRefreshing(false);
        showToast("Refresh!");
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
    public void onLoadMoreRequested() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mTestData.add(new Status("111"));
                mAdapter.loadMoreEnd();
                mAdapter.notifyDataSetChanged();
            }
        }, 3000);
    }

    private OnItemClickListener mOnitemClickListener = new OnItemClickListener() {

        @Override
        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            Log.d(TAG, "onSimpleItemClick: " + position);
            showToast(position + "");
        }

        @Override
        public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
            super.onItemLongClick(adapter, view, position);
            Log.d(TAG, "onItemLongClick: " + position);
            showToast("Long: " + position);
        }
    };

    private View.OnClickListener mRetryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showToast("Retry");
        }
    };
}
