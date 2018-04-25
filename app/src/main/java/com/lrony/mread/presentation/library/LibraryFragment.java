package com.lrony.mread.presentation.library;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.lrony.mread.R;
import com.lrony.mread.mvp.MvpFragment;

/**
 * Created by Lrony on 18-4-23.
 */
public class LibraryFragment extends MvpFragment<LibraryContract.Presenter> implements LibraryContract.View {

    private static final String TAG = "LibraryFragment";

    private Toolbar mToolbar;

    public static LibraryFragment newInstance() {
        Bundle args = new Bundle();
        LibraryFragment fragment = new LibraryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public LibraryContract.Presenter createPresenter() {
        return new LibraryPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_library;
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
        mToolbar = view.findViewById(R.id.library_toolbar);
        mToolbar.setTitle(R.string.library_title);
    }

    private void initListener() {
        Log.d(TAG, "initListener");
    }
}
