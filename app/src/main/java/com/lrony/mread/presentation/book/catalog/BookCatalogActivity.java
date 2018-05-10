package com.lrony.mread.presentation.book.catalog;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.classic.common.MultipleStatusView;
import com.lrony.mread.R;
import com.lrony.mread.data.net.BookChapterPackage;
import com.lrony.mread.mvp.MvpActivity;
import com.lrony.mread.ui.help.ProgressDialogHandler;
import com.lrony.mread.ui.help.ToolbarHelper;

import java.util.List;

public class BookCatalogActivity extends MvpActivity<BookCatalogContract.Presenter> implements BookCatalogContract.View {

    private static final String TAG = "BookCatalogActivity";
    private static final String K_EXTRA_BOOK = "book";

    private String mBookId;

    private MultipleStatusView mStatusView;
    private SwipeRefreshLayout mRefreshView;
    private RecyclerView mRecyclerView;

    private BookChapterPackage mChapter;

    private ProgressDialogHandler mDialogHandler;

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
        getPresenter().loadBookInfo(mBookId);
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

        mRefreshView.setColorSchemeResources(R.color.colorAccent);
    }

    private void initListener() {
        Log.d(TAG, "initListener");
    }

    private View.OnClickListener mRetryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @NonNull
    @Override
    public BookCatalogContract.Presenter createPresenter() {
        return new BookCatalogPresenter();
    }

    @Override
    public void loading() {
        super.loading();
        if (mDialogHandler != null) {
            mDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        } else {
            Log.d(TAG, "loading mDialogHandler is null");
        }
    }

    @Override
    public void complete() {
        super.complete();
        if (mDialogHandler != null) {
            mDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
        } else {
            Log.d(TAG, "complete mDialogHandler is null");
        }
    }

    @Override
    public void finshLoadBookInfo(BookChapterPackage chapter) {
        mChapter = chapter;
    }
}
