package com.lrony.mread.presentation.self;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.lrony.mread.App;
import com.lrony.mread.R;
import com.lrony.mread.mvp.MvpFragment;
import com.lrony.mread.pref.AppConfig;
import com.lrony.mread.util.Shares;

/**
 * Created by Lrony on 18-4-23.
 */
public class SelfFragment extends MvpFragment<SelfContract.Presenter> implements SelfContract.View, View.OnClickListener {

    private static final String TAG = "SelfFragment";

    private CompoundButton mSwNightMode;

    public static SelfFragment newInstance() {
        Bundle args = new Bundle();
        SelfFragment fragment = new SelfFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public SelfContract.Presenter createPresenter() {
        return new SelfPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_self;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 代码规范，必须调用
        getPresenter().start();

        initView(view);
        initListener(view);
    }

    private void initView(View view) {
        Log.d(TAG, "initView");
        mSwNightMode = view.findViewById(R.id.sw_night_mode);
        mSwNightMode.setChecked(AppConfig.isNightMode());
    }

    private void initListener(View view) {
        Log.d(TAG, "initListener");
        mSwNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                if (!b) {
                    activity
                            .getDelegate()
                            .setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    AppConfig.setNightMode(false);
                    mSwNightMode.setChecked(false);
                } else {
                    activity
                            .getDelegate()
                            .setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    AppConfig.setNightMode(true);
                    mSwNightMode.setChecked(true);
                }
                activity.recreate();
            }
        });
        bindOnClickLister(view, this
                , R.id.self_app_night_mode
                , R.id.self_app_share);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.self_app_night_mode:
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                if (AppConfig.isNightMode()) {
                    activity
                            .getDelegate()
                            .setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    AppConfig.setNightMode(false);
                    mSwNightMode.setChecked(false);
                } else {
                    activity
                            .getDelegate()
                            .setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    AppConfig.setNightMode(true);
                    mSwNightMode.setChecked(true);
                }
                activity.recreate();
                break;
            case R.id.self_app_share:
                Shares.share(getContext(), R.string.share_content);
                break;
        }
    }
}
