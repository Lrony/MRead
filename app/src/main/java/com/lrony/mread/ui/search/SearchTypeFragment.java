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
public class SearchTypeFragment extends BaseFragment {

    private static final String TAG = "SearchTypeFragment";

    public static final String SEARCH_TYPE_ID = "search_type_id";

    private String mSearchTypeID;

    public static SearchTypeFragment newInstance(String billId) {
        Bundle args = new Bundle();
        args.putString(SEARCH_TYPE_ID, billId);
        SearchTypeFragment fragment = new SearchTypeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init();
        View view = inflater.inflate(R.layout.fragment_search_type, container, false);
        return view;
    }

    private void init() {
        mSearchTypeID = getArguments().getString(SEARCH_TYPE_ID);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        if (findChildFragment(SearchTypeContentFragment.class) == null) {
            loadRootFragment(R.id.fl_container, SearchTypeContentFragment.newInstance(mSearchTypeID));
        }
    }
}
