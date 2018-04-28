package com.lrony.mread.presentation.book.recommend;

import com.lrony.mread.data.net.RecommendBooksPackage;
import com.lrony.mread.mvp.MvpPresenter;
import com.lrony.mread.mvp.MvpView;

/**
 * Created by Lrony on 18-4-28.
 */
public interface RecommendContract {

    interface View extends MvpView {

        void finshLoadRecommend(RecommendBooksPackage books);
    }

    interface Presenter extends MvpPresenter<View> {

        void loadRecommendBook(String id);
    }
}
