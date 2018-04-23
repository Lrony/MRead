package com.lrony.mread.presentation.self;

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
public class SelfFragment extends MvpFragment<SelfContract.Presenter> implements SelfContract.View {

    private static final String TAG = "SelfFragment";

    public static SelfFragment newInstance() {
        Bundle args = new Bundle();
        SelfFragment fragment = new SelfFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public SelfContract.Presenter createPresenter() {
        return new SelfPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_self;
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
