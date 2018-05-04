package com.lrony.mread.presentation.hot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.lrony.mread.R;
import com.lrony.mread.base.BaseFragment;

/**
 * Created by Lrony on 18-4-24.
 */
public class HotTypeFragment extends BaseFragment {

    private static final String TAG = "HotTypeFragment";
    public static final String SEARCH_TYPE_ID = "search_type_id";

    private String mSearchTypeID;

    public static HotTypeFragment newInstance(String billId) {
        Bundle args = new Bundle();
        args.putString(SEARCH_TYPE_ID, billId);
        HotTypeFragment fragment = new HotTypeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_framelayout;
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

        if (findChildFragment(HotTypeContentFragment.class) == null) {
            loadRootFragment(R.id.fl_container, HotTypeContentFragment.newInstance(mSearchTypeID));
        }
    }
}
