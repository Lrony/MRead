package com.lrony.mread.presentation.book;

import com.lrony.mread.data.net.RecommendBooksPackage;
import com.lrony.mread.mvp.MvpPresenter;
import com.lrony.mread.mvp.MvpView;

/**
 * Created by Lrony on 18-4-26.
 */
public interface BookDetailContract {

    interface View extends MvpView {

        void finshLoadRecommendBookList(RecommendBooksPackage books);
    }

    interface Presenter extends MvpPresenter<View> {

        void loadRecommendBookList(String id);
    }
}
