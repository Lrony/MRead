package com.lrony.mread.presentation.search;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;

import com.lrony.mread.R;
import com.lrony.mread.mvp.MvpActivity;
import com.lrony.mread.presentation.book.BookDetailActivity;

public class SearchActivity extends MvpActivity<SearchContract.Presenter> implements SearchContract.View {

    private static final String TAG = "SearchActivity";

    private static final String K_KEYWORD = "search";

    private String mDefaultSearch;

    public static Intent newIntent(Context context, String str) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(K_KEYWORD, str);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_search);
    }

    @NonNull
    @Override
    public SearchContract.Presenter createPresenter() {
        return new SearchPresenter();
    }

    private void init() {
        mDefaultSearch = getIntent().getStringExtra(K_KEYWORD);
    }
}
