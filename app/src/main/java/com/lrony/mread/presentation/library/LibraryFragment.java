package com.lrony.mread.presentation.library;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lrony.mread.mvp.MvpFragment;

/**
 * Created by Lrony on 18-4-23.
 */
public class LibraryFragment extends MvpFragment<LibraryContract.Presenter> implements LibraryContract.View {

    public static LibraryFragment newInstance() {
        Bundle args = new Bundle();
        LibraryFragment fragment = new LibraryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public LibraryContract.Presenter createPresenter() {
        return new LibraryPresenter();
    }

    @Override
    public int getLayoutId() {
        return 0;
    }
}
