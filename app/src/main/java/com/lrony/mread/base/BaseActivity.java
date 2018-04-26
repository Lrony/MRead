package com.lrony.mread.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;

import com.lrony.mread.pref.AppConfig;

/**
 * Created by lrony on 2018/4/9.
 * Providing functionality for all activity
 * <p>
 * 为所有的activity封装功能
 */
public class BaseActivity extends BaseSuperActivity {

    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
    }

    public void bindOnClickLister(View rootView, View.OnClickListener listener, @IdRes int... ids) {
        for (int id : ids) {
            View view = rootView.findViewById(id);
            if (view != null) {
                view.setOnClickListener(listener);
            }
        }
    }

    public void bindOnClickLister(View.OnClickListener listener, @IdRes int... ids) {
        for (int id : ids) {
            View view = findViewById(id);
            if (view != null) {
                view.setOnClickListener(listener);
            }
        }
    }

    public void bindOnClickLister(View.OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

    private void initTheme() {
        Log.d(TAG, "initTheme: " + AppConfig.isNightMode());
        if (AppConfig.isNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }
}
