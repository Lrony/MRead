package com.lrony.mread.presentation.search;

import android.support.annotation.NonNull;
import android.os.Bundle;

import com.lrony.mread.R;
import com.lrony.mread.mvp.MvpActivity;

public class SearchActivity extends MvpActivity<SearchContract.Presenter> implements SearchContract.View {

    private static final String TAG = "SearchActivity";

    private static final String K_KEYWORD = "search";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @NonNull
    @Override
    public SearchContract.Presenter createPresenter() {
        return new SearchPresenter();
    }
}
