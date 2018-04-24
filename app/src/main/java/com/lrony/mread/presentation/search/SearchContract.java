package com.lrony.mread.presentation.search;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.lrony.mread.mvp.MvpPresenter;
import com.lrony.mread.mvp.MvpView;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Lrony on 18-4-23.
 */
public interface SearchContract {

    interface View extends MvpView {

        void setTabContent(@NonNull SupportFragment[] fragments, @NonNull String[] titles);

        void setSelectedTab(String title);

        String getSelectedTab();

        void showMessage(@StringRes int id);

        void showError();

        void showLoading();

        void showContent();
    }

    interface Presenter extends MvpPresenter<View> {

        void loadData();
    }
}
