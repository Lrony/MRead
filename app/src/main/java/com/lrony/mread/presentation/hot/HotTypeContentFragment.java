package com.lrony.mread.presentation.hot;

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
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.classic.common.MultipleStatusView;
import com.lrony.mread.AppRouter;
import com.lrony.mread.R;
import com.lrony.mread.data.bean.Book;
import com.lrony.mread.mvp.MvpFragment;
import com.lrony.mread.presentation.book.BookAdapter;
import com.lrony.mread.ui.help.RecyclerViewItemDecoration;
import com.lrony.mread.ui.widget.CustomLoadMoreView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lrony on 18-4-24.
 */
public class HotTypeContentFragment extends MvpFragment<HotTypeContentContract.Presenter> implements HotTypeContentContract.View, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "HotTypeContentFragment";

    private MultipleStatusView mStatusView;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshView;

    private BookAdapter mBookAdapter;

    private List<Book> mBooks = new ArrayList<>();

    private String mGender = "male";
    private String mMajor = "";
    private String mType = "hot";
    private String mMinor = "";
    private int mStart = 0;
    private int mLimit = 15;

    public static HotTypeContentFragment newInstance(String billId) {
        Bundle args = new Bundle();
        args.putString(HotTypeFragment.SEARCH_TYPE_ID, billId);
        HotTypeContentFragment fragment = new HotTypeContentFragment();
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
        loadData(true);
    }

    private void initView(View view) {
        Log.d(TAG, "initView");
        mStatusView = view.findViewById(R.id.multiple_status_view);
        mStatusView.setOnRetryClickListener(mRetryClickListener);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRefreshView = view.findViewById(R.id.refresh_view);

        mRefreshView.setColorSchemeResources(R.color.colorAccent);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()
                , LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorForeground));
        mRecyclerView.addItemDecoration(new RecyclerViewItemDecoration.Builder(getContext())
                .color(ContextCompat.getColor(getContext(), R.color.colorDivider))
                .thickness(1)
                .create());
        mBookAdapter = new BookAdapter(mBooks, getContext());
        mBookAdapter.setLoadMoreView(new CustomLoadMoreView());
        mRecyclerView.setAdapter(mBookAdapter);

    }

    private void initListener() {
        Log.d(TAG, "initListener");

        mBookAdapter.setOnLoadMoreListener(this);
        mRecyclerView.addOnItemTouchListener(mOnitemClickListener);
        mRefreshView.setOnRefreshListener(this);
    }

    @NonNull
    @Override
    public HotTypeContentContract.Presenter createPresenter() {
        return new HotTypeContentPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_type_content;
    }

    private void init() {
        mMajor = getArguments().getString(HotTypeFragment.SEARCH_TYPE_ID);
        Log.d(TAG, "init: " + mMajor);
    }

    private void loadData(boolean showStatusView) {
        Log.d(TAG, "loadData: showStatusView=" + showStatusView);
        getPresenter().loadData(showStatusView, mGender, mType, mMajor, mMinor, mStart, mLimit);
    }

    private void loadMoreData() {
        Log.d(TAG, "loadMoreData");
        getPresenter().loadMoreData(mGender, mType, mMajor, mMinor, mStart, mLimit);
    }

    @Override
    public void onLoadMoreRequested() {
        Log.d(TAG, "onLoadMoreRequested");
        loadMoreData();
    }

    private View.OnClickListener mRetryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loadData(true);
        }
    };

    private OnItemClickListener mOnitemClickListener = new OnItemClickListener() {

        @Override
        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            Log.d(TAG, "onSimpleItemClick: " + position);
            AppRouter.showBookDetailActivity(getContext(), mBooks.get(position).getId());
        }

        @Override
        public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
            super.onItemLongClick(adapter, view, position);
            Log.d(TAG, "onItemLongClick: " + position);
        }
    };

    @Override
    public void onRefresh() {
        mStart = 0;
        loadData(false);
    }

    @Override
    public void finishLoad(ArrayList<Book> books) {
        Log.d(TAG, "finishLoad");
        if (books.isEmpty()) {
            mStatusView.showEmpty();
            return;
        }

        mBooks.clear();
        mBooks.addAll(books);
        mBookAdapter.notifyDataSetChanged();
        mStart = books.size();
    }

    @Override
    public void finishLoadMore(ArrayList<Book> books) {
        Log.d(TAG, "finishLoadMore");
        if (books.isEmpty()) {
            mBookAdapter.loadMoreEnd();
            return;
        }
        for (Book book : books) {
            mBooks.add(book);
        }
        mStart += mBooks.size();
    }

    @Override
    public void showLoadError() {
        Log.d(TAG, "showLoadError");
        mStatusView.showError();
    }

    @Override
    public void showLoadMoreError() {
        Log.d(TAG, "showLoadMoreError");
        mBookAdapter.loadMoreFail();
    }

    @Override
    public void loading() {
        super.loading();
        Log.d(TAG, "loading");
        mStatusView.showLoading();
    }

    @Override
    public void complete() {
        super.complete();
        Log.d(TAG, "complete");
        mStatusView.showContent();
        mBookAdapter.loadMoreComplete();
        mRefreshView.setRefreshing(false);
    }
}
