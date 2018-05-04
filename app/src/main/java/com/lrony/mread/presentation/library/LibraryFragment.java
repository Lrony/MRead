package com.lrony.mread.presentation.library;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lrony.mread.R;
import com.lrony.mread.mvp.MvpFragment;
import com.lrony.mread.presentation.main.MainActivity;
import com.lrony.mread.ui.help.BaseFragmentAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;
import q.rorbin.badgeview.Badge;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.SimpleTabAdapter;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * Created by Lrony on 18-4-23.
 */
public class LibraryFragment extends MvpFragment<LibraryContract.Presenter> implements LibraryContract.View {

    private static final String TAG = "LibraryFragment";

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private VerticalTabLayout mTabLayout;

    public static LibraryFragment newInstance() {
        Bundle args = new Bundle();
        LibraryFragment fragment = new LibraryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public LibraryContract.Presenter createPresenter() {
        return new LibraryPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_library;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 代码规范，必须调用
        getPresenter().start();

        initView(view);
        initListener();

        getPresenter().loadData();
    }

    private void initView(View view) {
        Log.d(TAG, "initView");
        mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.library_title);

        mViewPager = view.findViewById(R.id.viewpager);
        mTabLayout = view.findViewById(R.id.tablayout);
    }

    private void initListener() {
        Log.d(TAG, "initListener");
    }

    private void setAdapter(final PagerAdapter adapter) {
        mViewPager.setOffscreenPageLimit(adapter.getCount() - 1);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabSelected(0);
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
}
