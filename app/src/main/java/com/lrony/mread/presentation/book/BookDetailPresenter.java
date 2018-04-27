package com.lrony.mread.presentation.book;

import android.util.Log;

import com.lrony.mread.R;
import com.lrony.mread.data.net.BookApi;
import com.lrony.mread.data.net.BookDetailPackage;
import com.lrony.mread.data.net.RecommendBooksPackage;
import com.lrony.mread.mvp.MvpBasePresenter;
import com.lrony.mread.util.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lrony on 18-4-26.
 */
public class BookDetailPresenter extends MvpBasePresenter<BookDetailContract.View> implements BookDetailContract.Presenter {

    private static final String TAG = "BookDetailPresenter";

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

        Call<BookDetailPackage> call = bookApi.getBookDetail(id);
        call.enqueue(new Callback<BookDetailPackage>() {
            @Override
            public void onResponse(Call<BookDetailPackage> call, Response<BookDetailPackage> response) {
                // View无效
                if (!isViewAttached()) return;

                getView().finshLoadBookInfo(response.body());
            }

            @Override
            public void onFailure(Call<BookDetailPackage> call, Throwable t) {
                // View无效
                if (!isViewAttached()) return;

                getView().showToast("加载失败");
            }
        });
    }

    @Override
    public void loadRecommendBookList(String id) {
        Log.d(TAG, "loadRecommendBookList: " + id);
        // View无效
        if (!isViewAttached()) return;

        Call<RecommendBooksPackage> call = bookApi.getRecommendBooksPackage(id);
        call.enqueue(new Callback<RecommendBooksPackage>() {
            @Override
            public void onResponse(Call<RecommendBooksPackage> call, Response<RecommendBooksPackage> response) {
                // View无效
                if (!isViewAttached()) return;

                getView().finshLoadRecommendBookList(response.body());
            }

            @Override
            public void onFailure(Call<RecommendBooksPackage> call, Throwable t) {
                // View无效
                if (!isViewAttached()) return;

                getView().showToast(R.string.bookdetail_recommend_book_list_load_error);
            }
        });
    }
}
