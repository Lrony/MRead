package com.lrony.mread.presentation.search;

import com.lrony.mread.data.net.BookApi;
import com.lrony.mread.data.net.KeyWordPackage;
import com.lrony.mread.data.net.SearchBookPackage;
import com.lrony.mread.mvp.MvpBasePresenter;
import com.lrony.mread.util.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lrony on 18-4-27.
 */
public class SearchPresenter extends MvpBasePresenter<SearchContract.View> implements SearchContract.Presenter {

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
    public void loadKeyWord(String content) {
        // View无效
        if (!isViewAttached()) return;

        Call<KeyWordPackage> call = bookApi.getKeyWordPacakge(content);
        call.enqueue(new Callback<KeyWordPackage>() {
            @Override
            public void onResponse(Call<KeyWordPackage> call, Response<KeyWordPackage> response) {
                // View无效
                if (!isViewAttached()) return;

                getView().showKeyWord(response.body());
            }

            @Override
            public void onFailure(Call<KeyWordPackage> call, Throwable t) {
                // View无效
                if (!isViewAttached()) return;
            }
        });
    }

    @Override
    public void loadSearchBook(String content) {
        // View无效
        if (!isViewAttached()) return;

        Call<SearchBookPackage> call = bookApi.getSearchBookPackage(content);
        call.enqueue(new Callback<SearchBookPackage>() {
            @Override
            public void onResponse(Call<SearchBookPackage> call, Response<SearchBookPackage> response) {
                // View无效
                if (!isViewAttached()) return;

                getView().showSearchBook(response.body());
            }

            @Override
            public void onFailure(Call<SearchBookPackage> call, Throwable t) {
                // View无效
                if (!isViewAttached()) return;
            }
        });
    }
}
