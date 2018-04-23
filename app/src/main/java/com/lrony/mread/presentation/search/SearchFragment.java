package com.lrony.mread.presentation.search;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lrony.mread.mvp.MvpFragment;

/**
 * Created by Lrony on 18-4-23.
 */
public class SearchFragment extends MvpFragment<SearchContract.Presenter> implements SearchContract.View {

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
        return 0;
    }
}
