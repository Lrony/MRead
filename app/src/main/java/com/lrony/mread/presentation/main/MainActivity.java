package com.lrony.mread.presentation.main;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.lrony.mread.R;
import com.lrony.mread.mvp.MvpActivity;

public class MainActivity extends MvpActivity<MainContract.Presenter> implements MainContract.View {

    private static final String TAG = "MainActivity";

    private BottomNavigationBar mNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @NonNull
    @Override
    public MainContract.Presenter createPresenter() {
        return new MainPresenter();
    }
}
