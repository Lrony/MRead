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

    @Override
    public void loadData() {
        // View无效
        if (!isViewAttached()) return;

        Call<BookSortPackage> call = bookApi.getBookSortPackage();
        call.enqueue(new Callback<BookSortPackage>() {
            @Override
            public void onResponse(Call<BookSortPackage> call, Response<BookSortPackage> response) {
                // View无效
                if (!isViewAttached()) return;

                int size = response.body().getMale().size();
                SupportFragment[] fragments = null;
                String[] titles = null;
                fragments = new SupportFragment[size];
                titles = new String[size];
                for (int i = 0; i < size; i++) {
                    String name = response.body().getMale().get(i).getName();
                    fragments[i] = HotTypeFragment.newInstance(name);
                    titles[i] = name;
                }
                if (fragments != null) {
                    getView().setTabContent(fragments, titles);
                }
            }

            @Override
            public void onFailure(Call<BookSortPackage> call, Throwable t) {
                // View无效
                if (!isViewAttached()) return;
            }
        });
    }
}
