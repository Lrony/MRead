package com.lrony.mread.ui.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lrony.mread.AppRouter;
import com.lrony.mread.R;
import com.lrony.mread.adapter.SearchTypeAdapter;
import com.lrony.mread.data.bean.BookDetailPackage;
import com.lrony.mread.data.bean.SortBookPackage;
import com.lrony.mread.service.BookApi;
import com.lrony.mread.ui.base.BaseFragment;
import com.lrony.mread.ui.widget.LoadRecyclerView;
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
    private LoadRecyclerView mRecyclerView;

    private SortBookPackage mDatas;

    private SearchTypeAdapter mAdapter;

    private BookApi mBookApi;

    private boolean mIsLoadData = false;

    // 每页显示的数量
    private int mPageCount = 10;
    // 当前页数
    private static int mPage = 0;

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

        mAdapter.setOnItemClickListener(new SearchTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                getBookInfo(mDatas.getBooks().get(position).get_id());
            }
        });

        mRecyclerView.setonLoadMoreListener(new LoadRecyclerView.onLoadMore() {
            @Override
            public void onLoadMore() {
                if (!mIsLoadData) {
                    mPage += 1;
                    getBooks(mPage);
                }
            }
        });
    }

    private void initData() {
        getBooks(mPage);
    }

    private void getBooks(int page) {
        Log.d(TAG, "getBooks: page = " + page);
        mIsLoadData = true;
        mRefreshView.setRefreshing(true);
        // http://api.zhuishushenqi.com/book/by-categories
        // ?gender=male&type=hot&major=%E5%A5%87%E5%B9%BB&minor=&start=&limit=50
        Call<SortBookPackage> call = mBookApi.getSortBookPackage("male"
                , "hot", mSearchTypeID, "", mPage * mPageCount, mPageCount);
        call.enqueue(new Callback<SortBookPackage>() {
            @Override
            public void onResponse(Call<SortBookPackage> call, Response<SortBookPackage> response) {
                if (response.body() != null) {
                    if (mDatas == null) {
                        mDatas = response.body();
                    } else {
                        int bookcount = response.body().getBooks().size();
                        if (bookcount > 0) {
                            for (int i = 0; i < bookcount; i++) {
                                mDatas.getBooks().add(response.body().getBooks().get(i));
                            }
                        } else {
                            ToastUtil.showToast(R.string.book_type_no_more);
                        }
                    }
                    mAdapter.refreshItems(mDatas);
                }
                mRefreshView.setRefreshing(false);
                mIsLoadData = false;
            }

            @Override
            public void onFailure(Call<SortBookPackage> call, Throwable t) {
                ToastUtil.showToast(R.string.book_type_get_error);
                mRefreshView.setRefreshing(false);
                mIsLoadData = false;
            }
        });
    }

    private void getBookInfo(String id) {
        Call<BookDetailPackage> call = mBookApi.getBookDetail(id);
        call.enqueue(new Callback<BookDetailPackage>() {
            @Override
            public void onResponse(Call<BookDetailPackage> call, Response<BookDetailPackage> response) {
                AppRouter.showBookDetailActivity(getContext(), response.body());
            }

            @Override
            public void onFailure(Call<BookDetailPackage> call, Throwable t) {
                ToastUtil.showToast(R.string.book_info_get_error);
            }
        });
    }

    @Override
    public void onRefresh() {
        getBooks(mPage);
    }
}
