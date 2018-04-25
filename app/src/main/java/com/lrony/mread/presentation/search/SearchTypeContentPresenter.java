package com.lrony.mread.presentation.search;

import android.util.Log;

import com.lrony.mread.R;
import com.lrony.mread.data.bean.Book;
import com.lrony.mread.data.net.SortBookPackage;
import com.lrony.mread.data.net.BookApi;
import com.lrony.mread.mvp.MvpBasePresenter;
import com.lrony.mread.util.Constant;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lrony on 18-4-24.
 */
public class SearchTypeContentPresenter extends MvpBasePresenter<SearchTypeContentContract.View> implements SearchTypeContentContract.Presenter {

    private static final String TAG = "SearchTypeContentPresen";

    private BookApi bookApi;

    @Override
    public void start() {
        super.start();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        bookApi = retrofit.create(BookApi.class);
    }

    @Override
    public void loadData(final boolean firstLoad, String gender, String type, String major, String minor, int start, int limit) {
        Log.d(TAG, "loadData: firstLoad=" + firstLoad + ",gender=" + gender + ",type=" + type + ",major=" + major + ",minor=" + minor + ",start=" + start + ",limit=" + limit);

        // View无效
        if (!isViewAttached()) return;

        Call<SortBookPackage> call = bookApi.getSortBookPackage(gender, type, major, minor, start, limit);
        call.enqueue(new Callback<SortBookPackage>() {
            @Override
            public void onResponse(Call<SortBookPackage> call, Response<SortBookPackage> response) {
                // View无效
                if (!isViewAttached()) return;

                ArrayList<Book> books = new ArrayList<>();
                for (int i = 0; i < response.body().getBooks().size(); i++) {
                    SortBookPackage.BooksBean bookBean = response.body().getBooks().get(i);
                    Book book = new Book(bookBean.get_id()
                            , bookBean.getTitle()
                            , bookBean.getAuthor()
                            , bookBean.getShortIntro()
                            , bookBean.getCover()
                            , bookBean.getSite()
                            , bookBean.getMajorCate()
                            , bookBean.getMinorCate()
                            , bookBean.getContentType()
                            , bookBean.isAllowMonthly()
                            , bookBean.getBanned()
                            , bookBean.getLatelyFollower()
                            , bookBean.getRetentionRatio()
                            , bookBean.getLastChapter());
                    books.add(book);
                }

                if (firstLoad) {
                    getView().finishRefresh(books);
                    getView().complete();
                } else {
                    getView().finishLoad(books);
                }
            }

            @Override
            public void onFailure(Call<SortBookPackage> call, Throwable t) {
                // View无效
                if (!isViewAttached()) return;

                if (firstLoad) {
                    getView().showToast(R.string.search_type_date_get_error);
                    getView().complete();
                    getView().showRefreshError();
                } else {
                    getView().showToast(R.string.search_type_load_error);
                    getView().showLoadError();
                }
            }
        });
    }
}
