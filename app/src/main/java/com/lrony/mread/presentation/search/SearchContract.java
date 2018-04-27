package com.lrony.mread.presentation.search;

import com.lrony.mread.data.net.KeyWordPackage;
import com.lrony.mread.data.net.SearchBookPackage;
import com.lrony.mread.mvp.MvpPresenter;
import com.lrony.mread.mvp.MvpView;

/**
 * Created by Lrony on 18-4-27.
 */
public interface SearchContract {

    interface View extends MvpView {

        void showKeyWord(KeyWordPackage keywords);

        void showSearchBook(SearchBookPackage books);
    }

    interface Presenter extends MvpPresenter<View> {

        void loadKeyWord(String content);

        void loadSearchBook(String content);
    }
}
