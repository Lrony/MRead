package com.lrony.mread.presentation.read;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;

import com.lrony.mread.R;
import com.lrony.mread.data.DBManger;
import com.lrony.mread.data.bean.Book;
import com.lrony.mread.data.db.table.BookTb;
import com.lrony.mread.mvp.MvpActivity;
import com.lrony.mread.util.DataConvertUtil;

public class ReadActivity extends MvpActivity<ReadContract.Presenter> implements ReadContract.View {

    private static final String TAG = "ReadActivity";

    //适配5.0 以下手机可以正常显示vector图片
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private static final String K_EXTRA_BOOK_TB = "book_tb";

    private BookTb mBookTb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        mBookTb = getIntent().getParcelableExtra(K_EXTRA_BOOK_TB);
    }

    @NonNull
    @Override
    public ReadContract.Presenter createPresenter() {
        return new ReadPresenter();
    }

    public static Intent newIntent(Context context, Book book, Integer sectionIndex, String sectionId) {
        Intent intent = new Intent(context, ReadActivity.class);
        BookTb bookTb = DataConvertUtil.book2BookTb(book, null);
        bookTb.setLatestReadSection(sectionIndex);
        bookTb.setLatestReadSectionId(sectionId);
        intent.putExtra(K_EXTRA_BOOK_TB, bookTb);
        return intent;
    }

    public static Intent newIntent(Context context, BookTb bookTb) {
        BookTb dbBookTb = DBManger.getInstance().loadBookTbById(bookTb.getId());
        if (dbBookTb != null) {//优先使用数据库中的bookTb
            bookTb = dbBookTb;
        }
        Intent intent = new Intent(context, ReadActivity.class);
        intent.putExtra(K_EXTRA_BOOK_TB, (Parcelable) bookTb);
        return intent;
    }
}
