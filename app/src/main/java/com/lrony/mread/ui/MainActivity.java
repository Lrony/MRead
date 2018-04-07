package com.lrony.mread.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.lrony.mread.R;
import com.lrony.mread.ui.base.BaseActivity;
import com.lrony.mread.ui.book.BookFragment;
import com.lrony.mread.ui.me.MeFragment;
import com.lrony.mread.ui.search.SearchFragment;

import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    private static final String TAG = "MainActivity";

    private BottomNavigationBar mBottomNavigationBar;

    public static final int PAGE_BOOK = 0;
    public static final int PAGE_SEARCH = 1;
    public static final int PAGE_ME = 2;

    private SupportFragment[] mFragments = new SupportFragment[3];

    // 当前显示的页面
    private int mCurrentFrm = PAGE_BOOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fragment Init
        SupportFragment firstFragment = findFragment(BookFragment.class);
        if (firstFragment == null) {
            mFragments[PAGE_BOOK] = BookFragment.newInstance();
            mFragments[PAGE_SEARCH] = SearchFragment.newInstance();
            mFragments[PAGE_ME] = MeFragment.newInstance();

            loadMultipleRootFragment(R.id.framen_root, PAGE_BOOK,
                    mFragments[PAGE_BOOK],
                    mFragments[PAGE_SEARCH],
                    mFragments[PAGE_ME]);
        } else {
            mFragments[PAGE_BOOK] = firstFragment;
            mFragments[PAGE_SEARCH] = findFragment(SearchFragment.class);
            mFragments[PAGE_ME] = findFragment(MeFragment.class);
        }

        initView();
        initListener();
    }

    private void initView() {
        mBottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        mBottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_bottom_bar_book, R.string.man_bottom_bar_book))
                .addItem(new BottomNavigationItem(R.drawable.ic_bottom_bar_search, R.string.man_bottom_bar_search))
                .addItem(new BottomNavigationItem(R.drawable.ic_bottom_bar_me, R.string.man_bottom_bar_me))
                .setMode(BottomNavigationBar.MODE_DEFAULT)
                .initialise();
    }

    private void initListener() {
        mBottomNavigationBar.setTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case PAGE_BOOK:
                showHideFragment(mFragments[PAGE_BOOK], mFragments[mCurrentFrm]);
                mCurrentFrm = PAGE_BOOK;
                break;
            case PAGE_SEARCH:
                showHideFragment(mFragments[PAGE_SEARCH], mFragments[mCurrentFrm]);
                mCurrentFrm = PAGE_SEARCH;
                break;
            case PAGE_ME:
                showHideFragment(mFragments[PAGE_ME], mFragments[mCurrentFrm]);
                mCurrentFrm = PAGE_ME;
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
