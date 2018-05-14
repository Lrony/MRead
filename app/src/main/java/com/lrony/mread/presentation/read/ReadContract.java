package com.lrony.mread.presentation.read;

import com.lrony.mread.mvp.MvpPresenter;
import com.lrony.mread.mvp.MvpView;

/**
 * Created by Lrony on 18-5-14.
 */
public interface ReadContract {

    interface View extends MvpView {

    }

    interface Presenter extends MvpPresenter<View> {

    }
}
