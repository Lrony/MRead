package com.lrony.mread.presentation.hot;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.lrony.mread.R;
import com.lrony.mread.mvp.MvpActivity;

public class HotTypeSelectActivity extends MvpActivity<HotTypeSelectContract.Presenter> implements HotTypeSelectContract.View {

    private static final String TAG = "HotTypeSelectActivity";

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_type_select);
        getPresenter().start();

        initView();
        initListener();
    }

    @NonNull
    @Override
    public HotTypeSelectContract.Presenter createPresenter() {
        return new HotTypeSelectPresenter();
    }

    private void initView() {
        Log.d(TAG, "initView");
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.hot_type_select_title);
    }

    private void initListener() {
        Log.d(TAG, "initListener");
    }
}
