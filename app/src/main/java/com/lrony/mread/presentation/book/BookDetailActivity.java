package com.lrony.mread.presentation.book;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lrony.mread.R;
import com.lrony.mread.mvp.MvpActivity;

public class BookDetailActivity extends MvpActivity<BookDetailContract.Presenter> implements BookDetailContract.View {

    private static final String TAG = "BookDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
    }

    @NonNull
    @Override
    public BookDetailContract.Presenter createPresenter() {
        return new BookDetailPresenter();
    }
}
