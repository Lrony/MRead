package com.lrony.mread.presentation.hot;

import com.lrony.mread.data.bean.Book;
import com.lrony.mread.mvp.MvpPresenter;
import com.lrony.mread.mvp.MvpView;

import java.util.ArrayList;

/**
 * Created by Lrony on 18-4-24.
 */
public interface HotTypeContentContract {

    interface View extends MvpView {

        void finishLoad(ArrayList<Book> books);

        void finishLoadMore(ArrayList<Book> books);

        void showLoadError();

        void showLoadMoreError();
    }

    interface Presenter extends MvpPresenter<View> {

        /**
         *
         * @param showStatusView 是否显示StatusView
         * @param gender
         * @param type
         * @param major
         * @param minor
         * @param start
         * @param limit
         */
        void loadData(boolean showStatusView, String gender, String type, String major, String minor, int start, int limit);

        void loadMoreData(String gender, String type, String major, String minor, int start, int limit);
    }
}
