package com.lrony.mread.presentation.search;

import com.lrony.mread.data.bean.Book;
import com.lrony.mread.mvp.MvpPresenter;
import com.lrony.mread.mvp.MvpView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lrony on 18-4-24.
 */
public interface SearchTypeContentContract {

    interface View extends MvpView {

        void finishRefresh(ArrayList<Book> books);

        void finishLoad(ArrayList<Book> books);

        void showRefreshError();

        void showLoadError();

        void complete();
    }

    interface Presenter extends MvpPresenter<View> {

        /**
         *
         * @param firstLoad 是否为第一次加载x
         * @param gender
         * @param type
         * @param major
         * @param minor
         * @param start
         * @param limit
         */
        void loadData(boolean firstLoad, String gender, String type, String major, String minor, int start, int limit);
    }
}
