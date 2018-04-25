package com.lrony.mread.presentation.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.classic.common.MultipleStatusView;
import com.lrony.mread.R;
import com.lrony.mread.data.bean.Book;
import com.lrony.mread.mvp.MvpFragment;
import com.lrony.mread.presentation.book.BookAdapter;
import com.lrony.mread.ui.help.RecyclerViewItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lrony on 18-4-24.
 */
public class SearchTypeContentFragment extends MvpFragment<SearchTypeContentContract.Presenter> implements SearchTypeContentContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String TAG = "SearchTypeContentFragme";

    private MultipleStatusView mStatusView;
    private SwipeRefreshLayout mRefreshView;
    private RecyclerView mRecyclerView;

    private BookAdapter mBookAdapter;

    private List<Book> mBooks = new ArrayList<>();

    private String mGender = "male";
    private String mMajor = "";
    private String mType = "hot";
    private String mMinor = "";
    private int mStart = 0;
    private int mLimit = 15;

    public static SearchTypeContentFragment newInstance(String billId) {
        Bundle args = new Bundle();
        args.putString(SearchTypeFragment.SEARCH_TYPE_ID, billId);
        SearchTypeContentFragment fragment = new SearchTypeContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        getPresenter().start();

        initView(view);
        initListener();
        loadData(true, true);
    }

    private void initView(View view) {
        Log.d(TAG, "initView");
        mStatusView = view.findViewById(R.id.multiple_status_view);
        mStatusView.setOnRetryClickListener(mRetryClickListener);
        mRefreshView = view.findViewById(R.id.refresh_view);
        mRecyclerView = view.findViewById(R.id.recycler_view);

        mRefreshView.setColorSchemeResources(R.color.colorAccent);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()
                , LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorForeground));
        mRecyclerView.addItemDecoration(new RecyclerViewItemDecoration.Builder(getContext())
                .color(ContextCompat.getColor(getContext(), R.color.colorDivider))
                .thickness(1)
                .create());
        mBookAdapter = new BookAdapter(mBooks, getContext());
        mRecyclerView.setAdapter(mBookAdapter);

    }

    private void initListener() {
        Log.d(TAG, "initListener");
        mRefreshView.setOnRefreshListener(this);

        mBookAdapter.setOnLoadMoreListener(this);
    }

    @NonNull
    @Override
    public SearchTypeContentContract.Presenter createPresenter() {
        return new SearchTypeContentPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_type_content;
    }

    private void init() {
        mMajor = getArguments().getString(SearchTypeFragment.SEARCH_TYPE_ID);
        Log.d(TAG, "init: " + mMajor);
    }

    private View.OnClickListener mRetryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loadData(true, true);
        }
    };

    private void loadData(boolean firstLoad, boolean showRefreshView) {
        Log.d(TAG, "loadData: firstLoad=" + firstLoad + ",showRefreshView=" + showRefreshView);
        if (showRefreshView) mStatusView.showLoading();
        getPresenter().loadData(firstLoad, mGender, mType, mMajor, mMinor, mStart, mLimit);
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh");
        mStart = 0;
        loadData(true, false);
    }

    @Override
    public void onLoadMoreRequested() {
        Log.d(TAG, "onLoadMoreRequested");
        loadData(false, false);
    }

    @Override
    public void finishRefresh(ArrayList<Book> books) {
        Log.d(TAG, "finishRefresh: " + books);
        if (books.isEmpty()) {
            mStatusView.showEmpty();
            return;
        }
        mBooks.clear();
        mBooks.addAll(books);
        mBookAdapter.notifyDataSetChanged();
        mStatusView.showContent();
        mStart = books.size();
    }

    @Override
    public void finishLoad(ArrayList<Book> books) {
        Log.d(TAG, "finishLoad: " + books);
        for (Book book : books) {
            mBooks.add(book);
        }
        mBookAdapter.loadMoreComplete();
        mStart += mBooks.size();
    }

    @Override
    public void showRefreshError() {
        Log.d(TAG, "showRefreshError");
        mStatusView.showError();
    }

    @Override
    public void showLoadError() {
        Log.d(TAG, "showLoadError");
        mBookAdapter.loadMoreFail();
    }

    @Override
    public void complete() {
        Log.d(TAG, "complete");
        mRefreshView.setRefreshing(false);
    }
}
