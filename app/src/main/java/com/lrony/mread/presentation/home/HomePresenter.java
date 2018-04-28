package com.lrony.mread.presentation.home;

import android.util.Log;

import com.lrony.mread.data.DBManger;
import com.lrony.mread.data.db.table.BookTb;
import com.lrony.mread.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lrony on 18-4-23.
 */
public class HomePresenter extends MvpBasePresenter<HomeContract.View> implements HomeContract.Presenter {

    private static final String TAG = "HomePresenter";

    private List<BookTb> mBooks = new ArrayList<>();

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void doLoadData(boolean refresh) {
        // View无效
        if (!isViewAttached()) return;

        if (refresh) getView().loading();

        mBooks = DBManger.getInstance().loadAllBookTb();
        Log.d(TAG, "doLoadData mBooks size = " + mBooks.size());
        if (mBooks.size() <= 0) {
            getView().complete();
            getView().empty();
        } else {
            getView().finishLoad(mBooks);
            getView().complete();
        }

    }

    @Override
    public void deleteData(BookTb bookTb) {
        // View无效
        if (!isViewAttached()) return;

        DBManger.getInstance().deleteBookTb(bookTb);
        getView().finishDelete();
    }
}
