package com.lrony.mread.presentation.main;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.lrony.mread.R;
import com.lrony.mread.mvp.MvpActivity;
import com.lrony.mread.presentation.home.HomeFragment;
import com.lrony.mread.presentation.library.LibraryFragment;
import com.lrony.mread.presentation.search.SearchFragment;
import com.lrony.mread.presentation.self.SelfFragment;

import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends MvpActivity<MainContract.Presenter> implements MainContract.View, BottomNavigationBar.OnTabSelectedListener {

    private static final String TAG = "MainActivity";

    private BottomNavigationBar mNavigationBar;

    private SupportFragment[] mFragments = new SupportFragment[4];

    public static final int PAGE_HOME = 0;
    public static final int PAGE_SEARCH = 1;
    public static final int PAGE_LIBRARY = 2;
    public static final int PAGE_SELF = 3;

    // 当前显示的页面
    private int mCurrentFrm = PAGE_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPresenter().start();

        initFragment();
        initView();
        initListener();
    }

    private void initFragment() {
        // Fragment Init
        SupportFragment firstFragment = findFragment(HomeFragment.class);
        if (firstFragment == null) {
            mFragments[PAGE_HOME] = HomeFragment.newInstance();
            mFragments[PAGE_SEARCH] = SearchFragment.newInstance();
            mFragments[PAGE_LIBRARY] = LibraryFragment.newInstance();
            mFragments[PAGE_SELF] = SelfFragment.newInstance();

            loadMultipleRootFragment(R.id.framen_root, PAGE_HOME,
                    mFragments[PAGE_HOME],
                    mFragments[PAGE_SEARCH],
                    mFragments[PAGE_LIBRARY],
                    mFragments[PAGE_SELF]);
        } else {
            mFragments[PAGE_HOME] = firstFragment;
            mFragments[PAGE_SEARCH] = findFragment(SearchFragment.class);
            mFragments[PAGE_LIBRARY] = findFragment(LibraryFragment.class);
            mFragments[PAGE_SELF] = findFragment(SelfFragment.class);
        }
    }

    private void initView() {
        mNavigationBar = findViewById(R.id.bottom_navigation_bar);
        mNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_home, R.string.navigation_home))
                .addItem(new BottomNavigationItem(R.drawable.ic_hot, R.string.navigation_search))
                .addItem(new BottomNavigationItem(R.drawable.ic_list, R.string.navigation_library))
                .addItem(new BottomNavigationItem(R.drawable.ic_self, R.string.navigation_self))
                .setMode(BottomNavigationBar.MODE_FIXED)
                .initialise();
    }

    private void initListener() {
        mNavigationBar.setTabSelectedListener(this);
    }

    @NonNull
    @Override
    public MainContract.Presenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void onTabSelected(int position) {
        Log.d(TAG, "onTabSelected: " + position);
        switch (position) {
            case PAGE_HOME:
                showHideFragment(mFragments[PAGE_HOME], mFragments[mCurrentFrm]);
                mCurrentFrm = PAGE_HOME;
                break;
            case PAGE_SEARCH:
                showHideFragment(mFragments[PAGE_SEARCH], mFragments[mCurrentFrm]);
                mCurrentFrm = PAGE_SEARCH;
                break;
            case PAGE_LIBRARY:
                showHideFragment(mFragments[PAGE_LIBRARY], mFragments[mCurrentFrm]);
                mCurrentFrm = PAGE_LIBRARY;
                break;
            case PAGE_SELF:
                showHideFragment(mFragments[PAGE_SELF], mFragments[mCurrentFrm]);
                mCurrentFrm = PAGE_SELF;
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
