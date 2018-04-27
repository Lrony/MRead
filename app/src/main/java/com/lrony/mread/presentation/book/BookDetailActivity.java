package com.lrony.mread.presentation.book;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lrony.mread.R;
import com.lrony.mread.data.bean.Book;
import com.lrony.mread.data.net.RecommendBooksPackage;
import com.lrony.mread.mvp.MvpActivity;
import com.lrony.mread.ui.help.BaseRecyclerAdapter;
import com.lrony.mread.ui.help.ToolbarHelper;
import com.lrony.mread.ui.widget.ShapeTextView;
import com.lrony.mread.util.Constant;
import com.lrony.mread.util.ImageLoader;
import com.lrony.mread.util.StringUtils;

import java.text.ParseException;

public class BookDetailActivity extends MvpActivity<BookDetailContract.Presenter> implements BookDetailContract.View, View.OnClickListener {

    private static final String TAG = "BookDetailActivity";

    private static Book mBook;
    private RecommendBooksPackage mRecommendBooks;

    private RecommendBookAdapter mRecommendAdapter;

    private ImageView mIvCover;
    private TextView mTvReadCount;
    private ShapeTextView mTvIsFinish;
    private TextView mTvAuthor;
    private TextView mTvWordCount;
    private TextView mTvCatalogTitle;
    private TextView mTvUpdateTime;
    private TextView mTvDescribe;
    private RecyclerView mRvRecommendBook;
    private TextView mTvWordCountCopyright;
    private TextView mTvCreateDateCopyright;

    public static Intent newIntent(Context context, Book book) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        mBook = book;
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        getPresenter().start();
        ToolbarHelper.initToolbar(this, R.id.toolbar, true, mBook != null ? mBook.getTitle() : "书籍详情");

        initView();
        initListener();

        getPresenter().loadRecommendBookList(mBook.getId());
    }

    @NonNull
    @Override
    public BookDetailContract.Presenter createPresenter() {
        return new BookDetailPresenter();
    }

    private void initView() {
        mIvCover = findViewById(R.id.iv_cover);
        mTvReadCount = findViewById(R.id.tv_read_count);
        mTvIsFinish = findViewById(R.id.tv_is_finish);
        mTvAuthor = findViewById(R.id.tv_author);
        mTvWordCount = findViewById(R.id.tv_word_count);
        mTvCatalogTitle = findViewById(R.id.tv_catalog_title);
        mTvDescribe = findViewById(R.id.tv_describe);
        mTvWordCountCopyright = findViewById(R.id.tv_word_count_copyright);
        mTvCreateDateCopyright = findViewById(R.id.tv_create_date_copyright);
        mTvUpdateTime = findViewById(R.id.tv_update_time);
        mRvRecommendBook = findViewById(R.id.rv_recommend_book);
        mRvRecommendBook.setNestedScrollingEnabled(false);
        mRvRecommendBook.setLayoutManager(new GridLayoutManager(this, 3));
        mRecommendAdapter = new RecommendBookAdapter(this, mRecommendBooks, 6);
        mRvRecommendBook.setAdapter(mRecommendAdapter);

        initDisplay();
    }

    private void initListener() {
        bindOnClickLister(this, R.id.fl_add_bookcase, R.id.fl_download_book
                , R.id.fl_open_book, R.id.ll_book_detail_catalog);

        mRecommendAdapter.setItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showToast("" + position);
            }

            @Override
            public void onItemLongClick(int position) {
                showToast("Long: " + position);
            }
        });
    }

    private void initDisplay() {
        if (mBook == null) return;

        ImageLoader.load(this, Constant.IMG_BASE_URL + mBook.getCover(), mIvCover);
        mTvReadCount.setText(StringUtils.formatCount(mBook.getPostCount()) + "人看过");
        String isFinished = mBook.isFinished() ?
                getString(R.string.bookdetail_finished) : getString(R.string.bookdetail_not_finished);
        mTvAuthor.setText(mBook.getMinorCate() + " | " + mBook.getAuthor());
        mTvIsFinish.setText(isFinished);
        mTvWordCount.setText(isFinished + " | " + StringUtils.formatCount(mBook.getWordCount()) + "字");
        mTvWordCountCopyright.setText(mTvWordCountCopyright.getText() + StringUtils.formatCount(mBook.getWordCount()) + "字");
        mTvCatalogTitle.setText("最新章节: " + mBook.getLastChapter());
        try {
            mTvUpdateTime.setText(StringUtils.formatSomeAgo(StringUtils.dealDateFormat(mBook.getUpdated())) + "更新");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mTvCreateDateCopyright.setText(mTvCreateDateCopyright.getText().toString() + mBook.getRetentionRatio() + "%");
        mTvDescribe.setText(mBook.getShortIntro());

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void finshLoadRecommendBookList(RecommendBooksPackage books) {
        mRecommendBooks = books;
        mRecommendAdapter.refresh(mRecommendBooks);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBook = null;
        mRecommendBooks = null;
        mRecommendAdapter = null;

    }
}
