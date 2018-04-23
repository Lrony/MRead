package com.lrony.mread.presentation.search;

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
public class SearchFragment extends MvpFragment<SearchContract.Presenter> implements SearchContract.View {

    private static final String TAG = "SearchFragment";

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
        initListener();
    }

    private void initView(View view) {
        Log.d(TAG, "initView");
    }

    private void initListener() {
        Log.d(TAG, "initListener");
    }
}
