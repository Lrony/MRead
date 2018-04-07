package com.lrony.mread.ui.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lrony.mread.R;
import com.lrony.mread.ui.base.BaseFragment;

/**
 * Created by lrony on 2018/4/7.
 */
public class MeFragment extends BaseFragment {

    private static final String TAG = "MeFragment";

    public static MeFragment newInstance() {

        Bundle args = new Bundle();

        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {

    }

    private void initListener() {

    }
}
