package com.lrony.mread.presentation.book.recommend;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.classic.common.MultipleStatusView;
import com.lrony.mread.AppManager;
import com.lrony.mread.AppRouter;
import com.lrony.mread.R;
import com.lrony.mread.data.net.RecommendBooksPackage;
import com.lrony.mread.mvp.MvpActivity;
import com.lrony.mread.ui.help.BaseRecyclerAdapter;
import com.lrony.mread.ui.help.RecyclerViewItemDecoration;

public class RecommendActivity extends MvpActivity<RecommendContract.Presenter> implements RecommendContract.View {

    private static final String TAG = "RecommendActivity";

    private static final String K_EXTRA_RECOMMEND = "recommend";

    private String mBookId;

    private Toolbar mToolbar;
    private MultipleStatusView mStatusView;
    private SwipeRefreshLayout mRefreshView;
    private RecyclerView mRecyclerView;

    private RecommendBooksPackage mBooks;

    private RecommendAdapter mAdapter;

    public static Intent newIntent(Context context, String bookid) {
        Intent intent = new Intent(context, RecommendActivity.class);
        intent.putExtra(K_EXTRA_RECOMMEND, bookid);
        return intent;
    }

    private void init() {
        mBookId = getIntent().getStringExtra(K_EXTRA_RECOMMEND);
        Log.d(TAG, "init: " + mBookId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        init();
        getPresenter().start();

        initView();
        initListener();

        getPresenter().loadRecommendBook(mBookId);
    }

    @NonNull
    @Override
    public RecommendContract.Presenter createPresenter() {
        return new RecommendPresenter();
    }

    private void initView() {
        Log.d(TAG, "initView");
        mToolbar = findViewById(R.id.toolbar);
        mStatusView = findViewById(R.id.multiple_status_view);
        mStatusView.setOnRetryClickListener(mRetryClickListener);
        mRefreshView = findViewById(R.id.refresh_view);
        mRecyclerView = findViewById(R.id.recycler_view);

        mToolbar.setTitle(R.string.recommend_title);

        mRefreshView.setColorSchemeResources(R.color.colorAccent);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.colorDivider))
                .thickness(1)
                .create());

        mAdapter = new RecommendAdapter(this, null);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        Log.d(TAG, "initListener");
        mAdapter.setItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AppRouter.showBookDetailActivity(RecommendActivity.this, mBooks.getBooks().get(position).get_id());
                AppManager.getInstance().finishActivity();
            }

            @Override
            public void onItemLongClick(int position) {

            }
        });

    }

    private View.OnClickListener mRetryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showToast("Retry");
        }
    };

    @Override
    public void loading() {
        super.loading();
        mStatusView.showLoading();
    }

    @Override
    public void error() {
        super.error();
        mStatusView.showError();
    }

    @Override
    public void complete() {
        super.complete();
        mStatusView.showContent();
        mRefreshView.setRefreshing(false);
    }

    @Override
    public void finshLoadRecommend(RecommendBooksPackage books) {
        mBooks = books;
        mAdapter.refresh(books);
    }
}
