package com.lrony.mread.presentation.book.catalog;

import android.util.Log;
import com.lrony.mread.data.net.BookApi;
import com.lrony.mread.data.net.BookChapterPackage;
import com.lrony.mread.mvp.MvpBasePresenter;
import com.lrony.mread.util.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lrony on 18-5-10.
 */
public class BookCatalogPresenter extends MvpBasePresenter<BookCatalogContract.View> implements BookCatalogContract.Presenter {

    private static final String TAG = "BookCatalogPresenter";

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
    public void loadBookInfo(String id) {
        Log.d(TAG, "loadBookInfo: " + id);
        // View无效
        if (!isViewAttached()) return;

        getView().loading();
        Call<BookChapterPackage> call = bookApi.getBookChapterPackage(id, "chapters");
        call.enqueue(new Callback<BookChapterPackage>() {
            @Override
            public void onResponse(Call<BookChapterPackage> call, Response<BookChapterPackage> response) {
                // View无效
                if (!isViewAttached()) return;

                getView().finshLoadBookInfo(response.body());
                getView().complete();
            }

            @Override
            public void onFailure(Call<BookChapterPackage> call, Throwable t) {
                // View无效
                if (!isViewAttached()) return;

                getView().showToast("加载失败");
                getView().complete();
            }
        });
    }
}
