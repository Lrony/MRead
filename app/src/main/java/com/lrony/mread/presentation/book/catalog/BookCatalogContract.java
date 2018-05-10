package com.lrony.mread.presentation.book.catalog;

import com.lrony.mread.mvp.MvpPresenter;
import com.lrony.mread.mvp.MvpView;

/**
 * Created by Lrony on 18-5-10.
 */
public interface BookCatalogContract {

    interface View extends MvpView {

    }

    interface Presenter extends MvpPresenter<View> {

    }
}
