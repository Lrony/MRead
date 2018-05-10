package com.lrony.mread.presentation.book.catalog;

import com.lrony.mread.data.net.BookChapterPackage;
import com.lrony.mread.mvp.MvpPresenter;
import com.lrony.mread.mvp.MvpView;

import java.util.List;

/**
 * Created by Lrony on 18-5-10.
 */
public interface BookCatalogContract {

    interface View extends MvpView {

        void finshLoadBookInfo(BookChapterPackage chapter);
    }

    interface Presenter extends MvpPresenter<View> {

        void loadBookInfo(String id);
    }
}
