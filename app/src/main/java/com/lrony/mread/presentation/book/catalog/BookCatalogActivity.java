package com.lrony.mread.presentation.book.catalog;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lrony.mread.R;
import com.lrony.mread.data.bean.Book;
import com.lrony.mread.mvp.MvpActivity;
import com.lrony.mread.ui.help.ProgressDialogHandler;
import com.lrony.mread.ui.help.ToolbarHelper;

public class BookCatalogActivity extends MvpActivity<BookCatalogContract.Presenter> implements BookCatalogContract.View {

    private static final String TAG = "BookCatalogActivity";
    private static final String K_EXTRA_BOOK = "book";

    private String mBookId;

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
    }

    private void init() {
        mBookId = getIntent().getStringExtra(K_EXTRA_BOOK);
        Log.d(TAG, "init: " + mBookId);
    }

    private void initView() {
        Log.d(TAG, "initView");
        ToolbarHelper.initToolbar(this, R.id.toolbar, true, "目录");
    }

    private void initListener() {
        Log.d(TAG, "initListener");
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

    @NonNull
    @Override
    public BookCatalogContract.Presenter createPresenter() {
        return new BookCatalogPresenter();
    }
}
