package com.lrony.mread.presentation.book.catalog;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.classic.common.MultipleStatusView;
import com.lrony.mread.R;
import com.lrony.mread.data.net.BookChapterPackage;
import com.lrony.mread.mvp.MvpActivity;
import com.lrony.mread.ui.help.ProgressDialogHandler;
import com.lrony.mread.ui.help.RecyclerViewItemDecoration;
import com.lrony.mread.ui.help.ToolbarHelper;

import java.util.List;

public class BookCatalogActivity extends MvpActivity<BookCatalogContract.Presenter> implements BookCatalogContract.View, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "BookCatalogActivity";
    private static final String K_EXTRA_BOOK = "book";

    private String mBookId;

    private MultipleStatusView mStatusView;
    private SwipeRefreshLayout mRefreshView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    private BookCatalogAdapter mAdapter;

    private BookChapterPackage mChapter;

    public static Intent newIntent(Context context, String bookid) {
        Intent intent = new Intent(context, BookCatalogActivity.class);
        intent.putExtra(K_EXTRA_BOOK, bookid);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_book_catalog);
        getPresenter().start();
        initView();
        initListener();
        getPresenter().loadBookInfo(true, mBookId);
    }

    private void init() {
        mBookId = getIntent().getStringExtra(K_EXTRA_BOOK);
        Log.d(TAG, "init: " + mBookId);
    }

    private void initView() {
        Log.d(TAG, "initView");
        ToolbarHelper.initToolbar(this, R.id.toolbar, true, "目录");

        mRefreshView = findViewById(R.id.refresh_view);
        mStatusView = findViewById(R.id.multiple_status_view);
        mStatusView.setOnRetryClickListener(mRetryClickListener);
        mRecyclerView = findViewById(R.id.recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.colorDivider))
                .thickness(1)
                .create());

        mRefreshView.setColorSchemeResources(R.color.colorAccent);

        mAdapter = new BookCatalogAdapter(this, null);
        mRecyclerView.setAdapter(mAdapter);

        setRecyclerReverserder(true);
    }

    private void initListener() {
        Log.d(TAG, "initListener");

        mRefreshView.setOnRefreshListener(this);
    }

    private View.OnClickListener mRetryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getPresenter().loadBookInfo(true, mBookId);
        }
    };

    private void setRecyclerReverserder(boolean arg) {
        Log.d(TAG, "setRecyclerReverserder");
        if (mLinearLayoutManager != null) {
            mLinearLayoutManager.setStackFromEnd(arg);
            mLinearLayoutManager.setReverseLayout(arg);
        } else {
            Log.d(TAG, "setRecyclerReverserder mLinearLayoutManager is null");
        }

    }

    @NonNull
    @Override
    public BookCatalogContract.Presenter createPresenter() {
        return new BookCatalogPresenter();
    }

    @Override
    public void loading() {
        super.loading();
        mStatusView.showLoading();
    }

    @Override
    public void complete() {
        super.complete();
        mStatusView.showContent();
        mRefreshView.setRefreshing(false);
    }

    @Override
    public void error() {
        super.error();
        mStatusView.showError();
    }

    @Override
    public void empty() {
        super.empty();
        mStatusView.showEmpty();
    }

    @Override
    public void finshLoadBookInfo(BookChapterPackage chapter) {
        mChapter = chapter;
        mAdapter.refresh(mChapter);
    }

    @Override
    public void onRefresh() {
        getPresenter().loadBookInfo(false, mBookId);
    }
}
