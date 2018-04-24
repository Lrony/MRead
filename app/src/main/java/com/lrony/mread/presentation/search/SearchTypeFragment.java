package com.lrony.mread.presentation.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.lrony.mread.R;
import com.lrony.mread.base.BaseFragment;

/**
 * Created by Lrony on 18-4-24.
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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_type;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

    }

    private void init() {
        mSearchTypeID = getArguments().getString(SEARCH_TYPE_ID);
        Log.d(TAG, "init: " + mSearchTypeID);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        if (findChildFragment(SearchTypeContentFragment.class) == null) {
            loadRootFragment(R.id.fl_container, SearchTypeContentFragment.newInstance(mSearchTypeID));
        }
    }
}
