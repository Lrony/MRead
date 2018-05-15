package com.lrony.mread.presentation.read;

import com.lrony.mread.mvp.MvpPresenter;
import com.lrony.mread.mvp.MvpView;
import com.lrony.mread.presentation.book.catalog.BookCatalogAdapter;

/**
 * Created by Lrony on 18-5-14.
 */
public interface ReadContract {

    interface View extends MvpView {

        void setSectionListAdapter(ReadSectionAdapter adapter);
    }

    interface Presenter extends MvpPresenter<View> {

        void loadSectionList();

        void loadData();
    }
}
