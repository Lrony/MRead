package com.lrony.mread.presentation.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.classic.common.MultipleStatusView;
import com.lrony.mread.R;
import com.lrony.mread.mvp.MvpFragment;

/**
 * Created by Lrony on 18-4-24.
 */
public class SearchTypeContentFragment extends MvpFragment<SearchTypeContentContract.Presenter> implements SearchTypeContentContract.View {

    private static final String TAG = "SearchTypeContentFragme";

    private String mSearchTypeID;

    private MultipleStatusView mStatusView;
    private SwipeRefreshLayout mRefreshView;
    private RecyclerView mRecyclerView;

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
        getPresenter().start();

        initView(view);
        initListener();
    }

    private void initView(View view) {
        Log.d(TAG, "initView");
        mStatusView = view.findViewById(R.id.multiple_status_view);
        mStatusView.setOnRetryClickListener(mRetryClickListener);
        mRefreshView = view.findViewById(R.id.refresh_view);
        mRecyclerView = view.findViewById(R.id.recycler_view);

        mRefreshView.setColorSchemeResources(R.color.colorAccent);
    }

    private void initListener() {
        Log.d(TAG, "initListener");
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
        mSearchTypeID = getArguments().getString(SearchTypeFragment.SEARCH_TYPE_ID);
        Log.d(TAG, "init: " + mSearchTypeID);
    }

    private View.OnClickListener mRetryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showToast("Retry");
        }
    };
}
