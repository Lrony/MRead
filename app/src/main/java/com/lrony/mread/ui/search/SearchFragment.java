package com.lrony.mread.ui.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.lrony.mread.AppRouter;
import com.lrony.mread.R;
import com.lrony.mread.adapter.BaseFragmentAdapter;
import com.lrony.mread.data.bean.BookSortPackage;
import com.lrony.mread.service.BookApi;
import com.lrony.mread.ui.base.BaseFragment;
import com.lrony.mread.util.Constant;
import com.lrony.mread.util.ToastUtil;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lrony on 2018/4/7.
 */
public class SearchFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "SearchFragment";

    private ViewPager mViewPager;
    private RelativeLayout mSearchView;
    private TabLayout mTabView;
    private MaterialProgressBar mProgress;
    private AppCompatImageView mIvBookTypeMore, mIvBookTypeRetry;

    private BookApi mBookApi;

    private static final int STATUS_OK = 0;
    private static final int STATUS_ERROR = 1;
    private static final int STATUS_ING = 2;

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        init();
        initView(view);
        initListener();
        initData();
        return view;
    }

    private void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mBookApi = retrofit.create(BookApi.class);
    }

    private void initView(View view) {
        Log.d(TAG, "initView");
        mViewPager = view.findViewById(R.id.vp_explore);
        mSearchView = view.findViewById(R.id.view_search);
        mTabView = view.findViewById(R.id.tab_explore);
        mProgress = view.findViewById(R.id.progress);
        mIvBookTypeMore = view.findViewById(R.id.iv_book_type_more);
        mIvBookTypeRetry = view.findViewById(R.id.iv_book_type_retry);
    }

    private void initListener() {
        Log.d(TAG, "initListener");
        mSearchView.setOnClickListener(this);
        mIvBookTypeMore.setOnClickListener(this);
        mIvBookTypeRetry.setOnClickListener(this);
    }

    private void initData() {
        Log.d(TAG, "initData");
        getBookTypes();
    }

    private void getBookTypes() {
        refreshStatus(STATUS_ING);
        Call<BookSortPackage> call = mBookApi.getBookSortPackage();
        call.enqueue(new Callback<BookSortPackage>() {
            @Override
            public void onResponse(Call<BookSortPackage> call, Response<BookSortPackage> response) {
                if (response.body() != null) {
                    int size = response.body().getMale().size();
                    Fragment[] fragments = null;
                    String[] titles = null;
                    fragments = new Fragment[size];
                    titles = new String[size];
                    for (int i = 0; i < size; i++) {
                        String name = response.body().getMale().get(i).getName();
                        fragments[i] = SearchTypeFragment.newInstance(name);
                        titles[i] = name;
                    }

                    if (mViewPager.getAdapter() == null) {
                        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getChildFragmentManager());
                        adapter.setFragmentPages(fragments);
                        adapter.setPageTitles(titles);
                        setAdapter(adapter);
                    } else {
                        BaseFragmentAdapter adapter = (BaseFragmentAdapter) mViewPager.getAdapter();
                        adapter.setFragmentPages(fragments);
                        adapter.setPageTitles(titles);
                        mViewPager.setOffscreenPageLimit(adapter.getCount() - 1);
                        adapter.notifyDataSetChanged();
                    }
                    refreshStatus(STATUS_OK);
                } else {
                    ToastUtil.showToast("接口请求失败");
                    refreshStatus(STATUS_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BookSortPackage> call, Throwable t) {

            }
        });
    }

    private void setAdapter(PagerAdapter adapter) {
        mViewPager.setOffscreenPageLimit(adapter.getCount() - 1);
        mViewPager.setAdapter(adapter);
        mTabView.setupWithViewPager(mViewPager);
        setProgressShow(false);
    }

    // Type Refresh Status
    private void refreshStatus(int isok) {
        switch (isok) {
            case STATUS_OK:
                setProgressShow(false);
                setBookTypeRetryShow(false);
                setBookTypeMoreShow(true);
                break;
            case STATUS_ERROR:
                setProgressShow(false);
                setBookTypeRetryShow(true);
                setBookTypeMoreShow(false);
                break;
            case STATUS_ING:
                setProgressShow(true);
                setBookTypeRetryShow(false);
                setBookTypeMoreShow(false);
                break;
        }
    }

    private void setBookTypeMoreShow(boolean show) {
        if (show) {
            mIvBookTypeMore.setVisibility(View.VISIBLE);
        } else {
            mIvBookTypeMore.setVisibility(View.GONE);
        }
    }

    private void setBookTypeRetryShow(boolean show) {
        if (show) {
            mIvBookTypeRetry.setVisibility(View.VISIBLE);
        } else {
            mIvBookTypeRetry.setVisibility(View.GONE);
        }
    }

    private void setProgressShow(boolean show) {
        if (show) {
            mProgress.setVisibility(View.VISIBLE);
        } else {
            mProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_search:
                AppRouter.showSearchActivity(getContext());
                break;
            case R.id.iv_book_type_more:
                ToastUtil.showToast("Hello");
                break;
            case R.id.iv_book_type_retry:
                getBookTypes();
                break;
        }
    }
}
