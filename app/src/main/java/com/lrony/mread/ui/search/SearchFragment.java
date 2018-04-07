package com.lrony.mread.ui.search;

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
public class SearchFragment extends BaseFragment {

    private static final String TAG = "SearchFragment";

    public static SearchFragment newInstance() {

        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {

    }

    private void initListener() {

    }
}
