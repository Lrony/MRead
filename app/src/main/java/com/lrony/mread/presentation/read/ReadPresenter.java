package com.lrony.mread.presentation.read;

import android.util.Log;

import com.lrony.mread.data.db.table.BookTb;
import com.lrony.mread.data.net.BookApi;
import com.lrony.mread.data.net.ChapterInfoPackage;
import com.lrony.mread.mvp.MvpBasePresenter;
import com.lrony.mread.util.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lrony on 18-5-14.
 */
public class ReadPresenter extends MvpBasePresenter<ReadContract.View> implements ReadContract.Presenter {

    private static final String TAG = "ReadPresenter";

    private BookApi bookApi;

    private BookTb mBookTb;

    public ReadPresenter(BookTb bookTb) {
        this.mBookTb = bookTb;
    }

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
    public void loadData() {
        // View无效
        if (!isViewAttached()) return;

        Call<ChapterInfoPackage> call = bookApi.getChapterInfoPackage("http://book.my716.com/getBooks.aspx?method=content&bookId=42218&chapterFile=U_42218_201803191307571705_3288_3.txt");
        call.enqueue(new Callback<ChapterInfoPackage>() {
            @Override
            public void onResponse(Call<ChapterInfoPackage> call, Response<ChapterInfoPackage> response) {
                // View无效
                if (!isViewAttached()) return;

                Log.d(TAG, "onResponse: " + response.body().getChapter().getBody());
            }

            @Override
            public void onFailure(Call<ChapterInfoPackage> call, Throwable t) {
                // View无效
                if (!isViewAttached()) return;

                getView().showToast("Error");
            }
        });
    }
}
