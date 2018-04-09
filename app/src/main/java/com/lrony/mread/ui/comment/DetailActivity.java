package com.lrony.mread.ui.comment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lrony.mread.AppRouter;
import com.lrony.mread.R;
import com.lrony.mread.adapter.RecommendBooksAdapter;
import com.lrony.mread.data.bean.BookDetailPackage;
import com.lrony.mread.data.bean.RecommendBooksPackage;
import com.lrony.mread.service.BookApi;
import com.lrony.mread.ui.base.BaseActivity;
import com.lrony.mread.ui.help.ToolbarHelper;
import com.lrony.mread.ui.widget.ShapeTextView;
import com.lrony.mread.util.Constant;
import com.lrony.mread.util.StringUtils;
import com.lrony.mread.util.ToastUtil;
import com.squareup.picasso.Picasso;

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "DetailActivity";

    private static final String K_EXTRA_BOOK = "book_detail";

    private static BookDetailPackage mBook;
    private RecommendBooksPackage mRecommendBookList;

    private RecommendBooksAdapter mRecommendBookListAdapter;

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
    private TextView mTvRetentionRatioCopyright;

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
        initListener();
        initData();
    }

    private void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mBookApi = retrofit.create(BookApi.class);
    }

    private void initView() {
        mIvCover = (ImageView) findViewById(R.id.iv_cover);
        mTvReadCount = (TextView) findViewById(R.id.tv_read_count);
        mTvIsFinish = (ShapeTextView) findViewById(R.id.tv_is_finish);
        mTvAuthor = (TextView) findViewById(R.id.tv_author);
        mTvWordCount = (TextView) findViewById(R.id.tv_word_count);
        mTvCatalogTitle = (TextView) findViewById(R.id.tv_catalog_title);
        mTvDescribe = (TextView) findViewById(R.id.tv_describe);
        mTvWordCountCopyright = (TextView) findViewById(R.id.tv_word_count_copyright);
        mTvRetentionRatioCopyright = (TextView) findViewById(R.id.tv_retention_ratio_copyright);
        mTvUpdateTime = findViewById(R.id.tv_update_time);
        mRvRecommendBook = findViewById(R.id.rv_recommend_book);
        mRvRecommendBook.setNestedScrollingEnabled(false);
        bindOnClickLister(this, R.id.fl_add_bookcase, R.id.fl_download_book, R.id.fl_open_book, R.id.ll_book_detail_catalog);

        mRecommendBookListAdapter = new RecommendBooksAdapter(this, mRecommendBookList, 6);
        mRvRecommendBook.setLayoutManager(new GridLayoutManager(this, 3));
        mRvRecommendBook.setNestedScrollingEnabled(false);
        mRvRecommendBook.setAdapter(mRecommendBookListAdapter);
    }

    private void initListener() {
        mRecommendBookListAdapter.setOnItemClickListener(new RecommendBooksAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String bookid = mRecommendBookList.getBooks().get(position).get_id();
                Log.d(TAG, "onItemClick: bookid = " + bookid);
                getBookInfo(bookid);
            }
        });
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
        mTvRetentionRatioCopyright.setText(mTvRetentionRatioCopyright.getText() + mBook.getRetentionRatio() + "%");
        mTvDescribe.setText(mBook.getLongIntro());

        getRecommendBookList();
    }

    private void getBookInfo(String id) {
        Call<BookDetailPackage> call = mBookApi.getBookDetail(id);
        call.enqueue(new Callback<BookDetailPackage>() {
            @Override
            public void onResponse(Call<BookDetailPackage> call, Response<BookDetailPackage> response) {
                if (response.body().getTitle() == null) {
                    ToastUtil.showToast(R.string.book_info_get_error);
                    return;
                }
                Log.d(TAG, "onResponse: response.title = " + response.body().getTitle());
                AppRouter.showBookDetailActivity(DetailActivity.this, response.body());
                finish();
            }

            @Override
            public void onFailure(Call<BookDetailPackage> call, Throwable t) {
                ToastUtil.showToast(R.string.book_info_get_error);
            }
        });
    }

    private void getRecommendBookList() {
        if (mBook == null) return;

        Call<RecommendBooksPackage> call = mBookApi.getRecommendBooksPackage(mBook.get_id());
        call.enqueue(new Callback<RecommendBooksPackage>() {
            @Override
            public void onResponse(Call<RecommendBooksPackage> call, Response<RecommendBooksPackage> response) {
                mRecommendBookList = response.body();
                mRecommendBookListAdapter.refreshItems(mRecommendBookList);
            }

            @Override
            public void onFailure(Call<RecommendBooksPackage> call, Throwable t) {
                ToastUtil.showToast(R.string.book_recommend_get_error);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBook = null;
        mRecommendBookList = null;
    }
}
