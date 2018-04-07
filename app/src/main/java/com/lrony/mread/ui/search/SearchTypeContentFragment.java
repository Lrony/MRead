package com.lrony.mread.ui.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lrony.mread.R;
import com.lrony.mread.adapter.SearchTypeAdapter;
import com.lrony.mread.data.bean.SortBookPackage;
import com.lrony.mread.service.BookApi;
import com.lrony.mread.ui.base.BaseFragment;
import com.lrony.mread.util.Constant;
import com.lrony.mread.util.ToastUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lrony on 2018/4/7.
 */
public class SearchTypeContentFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "SearchTypeContentFragme";

    private String mSearchTypeID;

    private SwipeRefreshLayout mRefreshView;
    private RecyclerView mRecyclerView;

    private SortBookPackage mDatas;

    private SearchTypeAdapter mAdapter;

    private BookApi mBookApi;

    public static SearchTypeContentFragment newInstance(String billId) {
        Bundle args = new Bundle();
        args.putString(SearchTypeFragment.SEARCH_TYPE_ID, billId);
        SearchTypeContentFragment fragment = new SearchTypeContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_type_content, container, false);
        init();
        initView(view);
        initListener();
        initData();
        return view;
    }

    private void init() {
        mSearchTypeID = getArguments().getString(SearchTypeFragment.SEARCH_TYPE_ID);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mBookApi = retrofit.create(BookApi.class);
    }

    private void initView(View view) {
        mRefreshView = view.findViewById(R.id.refresh_search_type_content);
        mRecyclerView = view.findViewById(R.id.recycler_search_type_content);

        mAdapter = new SearchTypeAdapter(getContext(), mDatas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()
                , LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        mRefreshView.setOnRefreshListener(this);
    }

    private void initData() {
        getAllBook();
    }

    private void getAllBook() {
        mRefreshView.setRefreshing(true);
        // http://api.zhuishushenqi.com/book/by-categories
        // ?gender=male&type=hot&major=%E5%A5%87%E5%B9%BB&minor=&start=&limit=50
        Call<SortBookPackage> call = mBookApi.getSortBookPackage("male"
                , "hot", mSearchTypeID, "", 0, 10);
        call.enqueue(new Callback<SortBookPackage>() {
            @Override
            public void onResponse(Call<SortBookPackage> call, Response<SortBookPackage> response) {
                if (response.body() != null) {
                    mDatas = response.body();
                    mAdapter.refreshItems(mDatas);
                }
                mRefreshView.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<SortBookPackage> call, Throwable t) {
                ToastUtil.showToast("获取失败");
                mRefreshView.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        getAllBook();
    }
}
