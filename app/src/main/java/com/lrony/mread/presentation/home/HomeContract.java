package com.lrony.mread.presentation.home;

import com.lrony.mread.data.db.table.BookTb;
import com.lrony.mread.mvp.MvpPresenter;
import com.lrony.mread.mvp.MvpView;

import java.util.List;

/**
 * Created by Lrony on 18-4-23.
 */
public interface HomeContract {

    interface View extends MvpView {

        void finishLoad(List<BookTb> bookTbs);

        void finishDelete();
    }

    interface Presenter extends MvpPresenter<View> {

        /**
         * 是否显示刷新的状态
         * @param refresh
         */
        void doLoadData(boolean refresh);

        void deleteData(BookTb bookTb);
    }
}
