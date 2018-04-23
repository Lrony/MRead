package com.lrony.mread.presentation.main;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.lrony.mread.R;
import com.lrony.mread.mvp.MvpActivity;

public class MainActivity extends MvpActivity<MainContract.Presenter> implements MainContract.View, BottomNavigationBar.OnTabSelectedListener {

    private static final String TAG = "MainActivity";

    private BottomNavigationBar mNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPresenter().start();

        initView();
        initListener();
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

    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
