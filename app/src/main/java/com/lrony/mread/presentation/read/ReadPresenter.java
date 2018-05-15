package com.lrony.mread.presentation.read;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lrony.mread.data.db.table.BookTb;
import com.lrony.mread.data.net.BookApi;
import com.lrony.mread.data.net.BookChapterPackage;
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

    private Context mContext;
    private BookTb mBookTb;
    private String mBookId;
    // 目录
    private BookChapterPackage mBookChapters;
    // 目录Adapter
    private ReadSectionAdapter readSectionAdapter;

    public ReadPresenter(Context context, BookTb bookTb) {
        this.mContext = context;
        this.mBookTb = bookTb;
        this.mBookId = bookTb.getId();
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

    private ReadSectionAdapter createReadSectionAdapter(BookChapterPackage pakge) {

        readSectionAdapter = new ReadSectionAdapter(mContext, pakge);
        return readSectionAdapter;

//        bookSectionAdapter = new BookSectionAdapter(bookSectionItems);
//        bookSectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                BookSectionItem bookSectionItem = ((List<BookSectionItem>) adapter.getData()).get(position);
//                doLoadData(bookSectionItem.getSectionIndex(), bookSectionItem.getSectionId(), true);
//            }
//        });
//        return bookSectionAdapter;
    }

    @Override
    public void loadSectionList() {
        // View无效
        if (!isViewAttached()) return;

        Call<BookChapterPackage> call = bookApi.getBookChapterPackage(mBookId, "chapters");
        call.enqueue(new Callback<BookChapterPackage>() {
            @Override
            public void onResponse(Call<BookChapterPackage> call, Response<BookChapterPackage> response) {
                // View无效
                if (!isViewAttached()) return;

                mBookChapters = response.body();
                getView().setSectionListAdapter(createReadSectionAdapter(response.body()));
            }

            @Override
            public void onFailure(Call<BookChapterPackage> call, Throwable t) {
                // View无效
                if (!isViewAttached()) return;
            }
        });
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
