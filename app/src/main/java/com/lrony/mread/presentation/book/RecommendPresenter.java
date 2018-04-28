package com.lrony.mread.presentation.book;

import com.lrony.mread.data.net.BookApi;
import com.lrony.mread.data.net.RecommendBooksPackage;
import com.lrony.mread.mvp.MvpBasePresenter;
import com.lrony.mread.util.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lrony on 18-4-28.
 */
public class RecommendPresenter extends MvpBasePresenter<RecommendContract.View> implements RecommendContract.Presenter {

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
    public void loadRecommendBook(String id) {
        // View无效
        if (!isViewAttached()) return;

        getView().loading();

        Call<RecommendBooksPackage> call = bookApi.getRecommendBooksPackage(id);
        call.enqueue(new Callback<RecommendBooksPackage>() {
            @Override
            public void onResponse(Call<RecommendBooksPackage> call, Response<RecommendBooksPackage> response) {
                // View无效
                if (!isViewAttached()) return;

                getView().finshLoadRecommend(response.body());
                getView().complete();
            }

            @Override
            public void onFailure(Call<RecommendBooksPackage> call, Throwable t) {
                // View无效
                if (!isViewAttached()) return;

                getView().complete();
                getView().error();
            }
        });
    }
}
