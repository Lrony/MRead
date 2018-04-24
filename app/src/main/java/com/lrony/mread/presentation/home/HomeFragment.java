package com.lrony.mread.presentation.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.lrony.mread.R;
import com.lrony.mread.mvp.MvpFragment;

/**
 * Created by Lrony on 18-4-23.
 */
public class HomeFragment extends MvpFragment<HomeContract.Presenter> implements HomeContract.View
        , SwipeRefreshLayout.OnRefreshListener, Toolbar.OnMenuItemClickListener {

    private static final String TAG = "HomeFragment";

    private Toolbar mToolbar;
    private SwipeRefreshLayout mRefreshView;
    private RecyclerView mRecyclerView;

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
        mRefreshView = view.findViewById(R.id.home_refresh);
        mRecyclerView = view.findViewById(R.id.home_recycler);

        mToolbar.setTitle(R.string.app_title);
        mToolbar.inflateMenu(R.menu.menu_home);
        mRefreshView.setColorSchemeResources(R.color.colorAccent);
    }

    private void initListener() {
        Log.d(TAG, "initListener");
        mRefreshView.setOnRefreshListener(this);
        mToolbar.setOnMenuItemClickListener(this);
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
}
