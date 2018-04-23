package com.lrony.mread.presentation.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.lrony.mread.R;
import com.lrony.mread.mvp.MvpFragment;

/**
 * Created by Lrony on 18-4-23.
 */
public class HomeFragment extends MvpFragment<HomeContract.Presenter> implements HomeContract.View {

    private static final String TAG = "HomeFragment";

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
    }

    private void initListener() {
        Log.d(TAG, "initListener");
    }
}
