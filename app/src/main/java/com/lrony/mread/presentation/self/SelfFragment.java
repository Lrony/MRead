package com.lrony.mread.presentation.self;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lrony.mread.mvp.MvpFragment;

/**
 * Created by Lrony on 18-4-23.
 */
public class SelfFragment extends MvpFragment<SelfContract.Presenter> implements SelfContract.View {

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
        return 0;
    }
}
