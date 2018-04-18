package com.lrony.mread.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Lrony on 18-4-10.
 * Manage all Fragment lifecycle, such as statistics and recovery.
 */
public class BaseSuperFragment extends SupportFragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Statsu recovery
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Status save
    }
}
