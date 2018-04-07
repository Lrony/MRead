package com.lrony.mread.ui.comment;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lrony.mread.R;
import com.lrony.mread.data.bean.BookDetailPackage;
import com.lrony.mread.service.BookApi;
import com.lrony.mread.ui.base.BaseActivity;
import com.lrony.mread.ui.help.ToolbarHelper;
import com.lrony.mread.ui.widget.ShapeTextView;
import com.lrony.mread.util.Constant;
import com.lrony.mread.util.StringUtils;
import com.lrony.mread.util.ToastUtil;
import com.squareup.picasso.Picasso;

import java.text.ParseException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "DetailActivity";

    private static final String K_EXTRA_BOOK = "book_detail";

    private static BookDetailPackage mBook;

    private Toolbar mToolbar;
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

    private BookApi mBookApi;

    public static Intent newIntent(Context context, BookDetailPackage book) {
        Intent intent = new Intent(context, DetailActivity.class);
        mBook = book;
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ToolbarHelper.initToolbar(this, R.id.toolbar, true, mBook != null ? mBook.getTitle() : "书籍详情");
        init();
        initView();
        initData();
    }

    private void init() {
//        mBook = getIntent().getParcelableExtra(K_EXTRA_BOOK);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mBookApi = retrofit.create(BookApi.class);
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mIvCover = (ImageView) findViewById(R.id.iv_cover);
        mTvReadCount = (TextView) findViewById(R.id.tv_read_count);
        mTvIsFinish = (ShapeTextView) findViewById(R.id.tv_is_finish);
        mTvAuthor = (TextView) findViewById(R.id.tv_author);
        mTvWordCount = (TextView) findViewById(R.id.tv_word_count);
        mTvCatalogTitle = (TextView) findViewById(R.id.tv_catalog_title);
        mTvDescribe = (TextView) findViewById(R.id.tv_describe);
        mTvWordCountCopyright = (TextView) findViewById(R.id.tv_word_count_copyright);
        mTvCreateDateCopyright = (TextView) findViewById(R.id.tv_create_date_copyright);
        mTvUpdateTime = findViewById(R.id.tv_update_time);
        mRvRecommendBook = findViewById(R.id.rv_recommend_book);
        mRvRecommendBook.setNestedScrollingEnabled(false);
        bindOnClickLister(this, R.id.fl_add_bookcase, R.id.fl_download_book, R.id.fl_open_book, R.id.ll_book_detail_catalog);

    }

    private void initData() {
        if (mBook == null) return;

        Picasso.get()
                .load(Constant.IMG_BASE_URL + mBook.getCover())
                .resize(82, 110)
                .into(mIvCover);
        mTvReadCount.setText(StringUtils.formatCount(mBook.getPostCount()) + "人读过");
        mTvAuthor.setText(mBook.getMinorCate() + " | " + mBook.getAuthor());
        mTvIsFinish.setText(mBook.is_gg() ?
                getString(R.string.book_finished) : getString(R.string.book_unfinished));
        mTvWordCount.setText(StringUtils.formatCount(mBook.getWordCount()) + "字");
        mTvCatalogTitle.setText("最新章节: " + mBook.getLastChapter());
        try {
            mTvUpdateTime.setText(StringUtils.formatSomeAgo(StringUtils.dealDateFormat(mBook.getUpdated())) + "更新");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mTvWordCountCopyright.setText(mTvWordCountCopyright.getText() + StringUtils.formatCount(mBook.getWordCount()) + "字");
        mTvCreateDateCopyright.setText(mTvCreateDateCopyright.getText() + "--");
        mTvDescribe.setText(mBook.getLongIntro());
    }

    @Override
    public void onClick(View v) {

    }
}
