package com.lrony.mread.presentation.main;

import com.lrony.mread.mvp.MvpPresenter;
import com.lrony.mread.mvp.MvpView;

/**
 * Created by Lrony on 18-4-23.
 */
public interface MainContract {

    interface View extends MvpView {

    }

    interface Presenter extends MvpPresenter<View> {

    }
}
