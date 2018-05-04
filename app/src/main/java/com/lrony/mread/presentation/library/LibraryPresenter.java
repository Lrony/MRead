package com.lrony.mread.presentation.library;

import com.lrony.mread.data.net.BookApi;
import com.lrony.mread.data.net.BookSortPackage;
import com.lrony.mread.mvp.MvpBasePresenter;
import com.lrony.mread.presentation.hot.HotTypeFragment;
import com.lrony.mread.util.Constant;

import me.yokeyword.fragmentation.SupportFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lrony on 18-4-23.
 */
public class LibraryPresenter extends MvpBasePresenter<LibraryContract.View> implements LibraryContract.Presenter {

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
}
