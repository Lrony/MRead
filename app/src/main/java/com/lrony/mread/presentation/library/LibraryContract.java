package com.lrony.mread.presentation.library;

import android.support.annotation.NonNull;

import com.lrony.mread.mvp.MvpPresenter;
import com.lrony.mread.mvp.MvpView;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Lrony on 18-4-23.
 */
public interface LibraryContract {

    interface View extends MvpView {

        void setTabContent(@NonNull SupportFragment[] fragments, @NonNull String[] titles);
    }

    interface Presenter extends MvpPresenter<View> {

        void loadData();
    }
}
