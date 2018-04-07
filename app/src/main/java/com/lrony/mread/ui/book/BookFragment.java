package com.lrony.mread.ui.book;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lrony.mread.R;
import com.lrony.mread.ui.base.BaseFragment;

/**
 * Created by lrony on 2018/4/7.
 */
public class BookFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "BookFragment";

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshView;

    public static BookFragment newInstance() {

        Bundle args = new Bundle();

        BookFragment fragment = new BookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        mRefreshView = view.findViewById(R.id.refresh);
        mRecyclerView = view.findViewById(R.id.recycler);

        mToolbar.setTitle(R.string.app_title);
        mToolbar.inflateMenu(R.menu.menu_book);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()
                , LinearLayoutManager.VERTICAL, false));
    }

    private void initListener() {
        mRefreshView.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mRefreshView.setRefreshing(false);
    }
}
