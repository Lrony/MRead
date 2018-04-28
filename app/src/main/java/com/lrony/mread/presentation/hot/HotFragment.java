package com.lrony.mread.presentation.hot;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;

import com.classic.common.MultipleStatusView;
import com.lrony.mread.AppRouter;
import com.lrony.mread.R;
import com.lrony.mread.mvp.MvpFragment;
import com.lrony.mread.ui.help.BaseFragmentAdapter;

import me.yokeyword.fragmentation.SupportFragment;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by Lrony on 18-4-23.
 */
public class HotFragment extends MvpFragment<HotContract.Presenter> implements HotContract.View, View.OnClickListener {

    private static final String TAG = "HotFragment";

    private ViewPager mViewPager;
    private TabLayout mTab;
    private MaterialProgressBar mProgress;
    private AppCompatImageView mImgType, mImgRetry;
    private MultipleStatusView mStatusView;

    public static HotFragment newInstance() {
        Bundle args = new Bundle();
        HotFragment fragment = new HotFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public HotContract.Presenter createPresenter() {
        return new HotPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 代码规范，必须调用
        getPresenter().start();

        initView(view);
        initListener(view);

        getPresenter().loadData();
    }

    private void initView(View view) {
        Log.d(TAG, "initView");
        mViewPager = view.findViewById(R.id.vp_search);
        mTab = view.findViewById(R.id.tab_search);
        mProgress = view.findViewById(R.id.progress);
        mImgType = view.findViewById(R.id.iv_book_type_more);
        mImgRetry = view.findViewById(R.id.iv_book_type_retry);
        mStatusView = view.findViewById(R.id.multiple_status_view);
        mStatusView.setOnRetryClickListener(mRetryClickListener);
    }

    private void initListener(View view) {
        Log.d(TAG, "initListener");
        view.findViewById(R.id.view_search).setOnClickListener(this);
        view.findViewById(R.id.fl_action).setOnClickListener(this);
    }

    private void setAdapter(final PagerAdapter adapter) {
        mViewPager.setOffscreenPageLimit(adapter.getCount() - 1);
        mViewPager.setAdapter(adapter);
        mTab.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_search:
                Log.d(TAG, "onClick: Search");
                AppRouter.showSearchActivity(getContext());
                break;
            case R.id.fl_action:
                Log.d(TAG, "onClick: Action");
                if (mImgType.getVisibility() == View.VISIBLE) {
                    showToast("Type");
                } else if (mImgRetry.getVisibility() == View.VISIBLE) {
                    getPresenter().loadData();
                }
                break;
        }
    }

    @Override
    public void setTabContent(@NonNull SupportFragment[] fragments, @NonNull String[] titles) {
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
    }

    @Override
    public void setSelectedTab(String title) {
        BaseFragmentAdapter adapter = (BaseFragmentAdapter) mViewPager.getAdapter();
        String[] pageTitles = adapter.getPageTitles();
        for (int i = 0; i < pageTitles.length; i++) {
            if (pageTitles[i].equals(title)) {
                mViewPager.setCurrentItem(i, false);
                break;
            }
        }
    }

    @Override
    public String getSelectedTab() {
        BaseFragmentAdapter adapter = (BaseFragmentAdapter) mViewPager.getAdapter();
        return adapter.getPageTitles()[mViewPager.getCurrentItem()];
    }

    private View.OnClickListener mRetryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getPresenter().loadData();
        }
    };

    @Override
    public void showError() {
        mProgress.setVisibility(View.GONE);
        mImgType.setVisibility(View.GONE);
        mImgRetry.setVisibility(View.VISIBLE);
        mStatusView.showError();
    }

    @Override
    public void showLoading() {
        mProgress.setVisibility(View.VISIBLE);
        mImgType.setVisibility(View.GONE);
        mImgRetry.setVisibility(View.GONE);
        mStatusView.showLoading();
    }

    @Override
    public void showContent() {
        mProgress.setVisibility(View.GONE);
        mImgType.setVisibility(View.VISIBLE);
        mImgRetry.setVisibility(View.GONE);
        mStatusView.showContent();
    }
}
