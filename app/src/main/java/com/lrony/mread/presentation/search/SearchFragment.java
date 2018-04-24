package com.lrony.mread.presentation.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.lrony.mread.R;
import com.lrony.mread.mvp.MvpFragment;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by Lrony on 18-4-23.
 */
public class SearchFragment extends MvpFragment<SearchContract.Presenter> implements SearchContract.View, View.OnClickListener {

    private static final String TAG = "SearchFragment";

    private ViewPager mViewPager;
    private TabLayout mTab;
    private MaterialProgressBar mProgress;

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public SearchContract.Presenter createPresenter() {
        return new SearchPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 代码规范，必须调用
        getPresenter().start();

        initView(view);
        initListener(view);
    }

    private void initView(View view) {
        Log.d(TAG, "initView");
        mViewPager = view.findViewById(R.id.vp_search);
        mTab = view.findViewById(R.id.tab_search);
        mProgress = view.findViewById(R.id.progress);
    }

    private void initListener(View view) {
        Log.d(TAG, "initListener");
        view.findViewById(R.id.view_search).setOnClickListener(this);
        view.findViewById(R.id.fl_action).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_search:
                Log.d(TAG, "onClick: Search");
                showToast("Search");
                break;
            case R.id.fl_action:
                Log.d(TAG, "onClick: Action");
                showToast("Action");
                break;
        }
    }
}
