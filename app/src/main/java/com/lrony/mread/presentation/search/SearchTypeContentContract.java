package com.lrony.mread.presentation.search;

import com.lrony.mread.data.bean.Book;
import com.lrony.mread.mvp.MvpPresenter;
import com.lrony.mread.mvp.MvpView;

import java.util.List;

/**
 * Created by Lrony on 18-4-24.
 */
public interface SearchTypeContentContract {

    interface View extends MvpView {

        /**
         * 显示内容VIEW
         * @param books
         */
        void showContent(List<Book> books);

        /**
         * 显示加载中VIEW
         */
        void showLoading();

        /**
         * 显示错误VIEW
         */
        void showError();

        /**
         * 显示无内容VIEW
         */
        void showEmpty();

        /**
         * 显示网络异常VIEW
         */
        void showNoNetWork();

    }

    interface Presenter extends MvpPresenter<View> {

        /**
         * @param refresh 是否需要显示刷新控件
         * @param gender
         * @param type
         * @param major
         * @param minor
         * @param start
         * @param limit
         */
        void loadData(boolean refresh, String gender, String type, String major, String minor, int start, int limit);
    }
}
