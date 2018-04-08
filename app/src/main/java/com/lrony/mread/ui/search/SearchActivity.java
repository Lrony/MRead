package com.lrony.mread.ui.search;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lrony.mread.AppRouter;
import com.lrony.mread.R;
import com.lrony.mread.adapter.KeyWordAdapter;
import com.lrony.mread.adapter.SearchAdapter;
import com.lrony.mread.data.bean.BookDetailPackage;
import com.lrony.mread.data.bean.KeyWordPackage;
import com.lrony.mread.data.bean.SearchBookPackage;
import com.lrony.mread.service.BookApi;
import com.lrony.mread.ui.base.BaseActivity;
import com.lrony.mread.util.Constant;
import com.lrony.mread.util.ToastUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SearchActivity";

    private static final String K_KEYWORD = "keyword";

    private ImageView mIBtnBack, mIBtnSearch, mIBtnClear;
    private EditText mEdtSearch;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshView;

    private KeyWordAdapter mKeyWordAdapter;
    private SearchAdapter mSearchAdapter;

    private KeyWordPackage mKeyWordDatas;
    private SearchBookPackage mSearchDatas;

    private BookApi mBookApi;

    public static Intent newIntent(Context context, @Nullable String keyword) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(K_KEYWORD, keyword);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();
        initView();
        initLinstener();

        bindOnClickLister(this, mIBtnBack, mIBtnSearch, mIBtnClear);
        String keyword = getIntent().getStringExtra(K_KEYWORD);
        if (keyword != null) {
            mEdtSearch.setText(keyword);
            mEdtSearch.setSelection(keyword.length());
        }
    }

    private void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mBookApi = retrofit.create(BookApi.class);
    }

    private void initView() {
        mIBtnBack = findViewById(R.id.iv_arrow_back);
        mIBtnSearch = findViewById(R.id.iv_action_search);
        mIBtnClear = findViewById(R.id.iv_action_clear);
        mEdtSearch = findViewById(R.id.et_search);
        mRecyclerView = findViewById(R.id.recycler);
        mRefreshView = findViewById(R.id.refresh);

        mKeyWordAdapter = new KeyWordAdapter(this, mKeyWordDatas);
        mSearchAdapter = new SearchAdapter(this, mSearchDatas);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this
                , LinearLayoutManager.VERTICAL, false));

    }

    private void initLinstener() {
        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().equals("")) {
                    if (mIBtnClear.getVisibility() == View.VISIBLE) {
                        mIBtnClear.setVisibility(View.GONE);
                        // Clear
                        mKeyWordAdapter.clear();
                        mSearchAdapter.clear();
                        mRecyclerView.removeAllViews();
                    }
                    return;
                }
                // Show Clear Btn
                if (mIBtnClear.getVisibility() == View.GONE) {
                    mIBtnClear.setVisibility(View.VISIBLE);
                }

                // auto put keywords
                keyWords();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEdtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideSoftKeyInput(mEdtSearch.getWindowToken());
                    searchBook();
                    return true;
                }
                return false;
            }
        });

        mKeyWordAdapter.setOnItemClickListener(new KeyWordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mEdtSearch.setText(mKeyWordDatas.getKeywords().get(position));
                mEdtSearch.setSelection(mEdtSearch.getText().length());
                searchBook();
            }
        });

        mRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchBook();
            }
        });

        mSearchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                getBookInfo(mSearchDatas.getBooks().get(position).get_id());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_back:
                onBackPressed();
                break;
            case R.id.iv_action_search:
                searchBook();
                break;
            case R.id.iv_action_clear:
                mEdtSearch.setText("");
                showSoftKeyInput(mEdtSearch.getWindowToken());
                break;

        }
    }

    private void getBookInfo(String id) {
        Call<BookDetailPackage> call = mBookApi.getBookDetail(id);
        call.enqueue(new Callback<BookDetailPackage>() {
            @Override
            public void onResponse(Call<BookDetailPackage> call, Response<BookDetailPackage> response) {
                AppRouter.showBookDetailActivity(SearchActivity.this, response.body());
            }

            @Override
            public void onFailure(Call<BookDetailPackage> call, Throwable t) {
                ToastUtil.showToast("书籍详情获取失败");
            }
        });
    }

    private void keyWords() {
        String query = mEdtSearch.getText().toString().trim();
        if (query.equals("")) return;
        Call<KeyWordPackage> call = mBookApi.getKeyWordPacakge(query);
        call.enqueue(new Callback<KeyWordPackage>() {
            @Override
            public void onResponse(Call<KeyWordPackage> call, Response<KeyWordPackage> response) {
                mKeyWordDatas = response.body();
                mKeyWordAdapter.refreshItems(response.body());
                mRecyclerView.setAdapter(mKeyWordAdapter);
            }

            @Override
            public void onFailure(Call<KeyWordPackage> call, Throwable t) {

            }
        });
    }

    private void searchBook() {
        String query = mEdtSearch.getText().toString().trim();
        if (query.equals("")) return;
        Call<SearchBookPackage> call = mBookApi.getSearchBookPackage(query);
        call.enqueue(new Callback<SearchBookPackage>() {
            @Override
            public void onResponse(Call<SearchBookPackage> call, Response<SearchBookPackage> response) {
                mRefreshView.setRefreshing(false);
                mSearchDatas = response.body();
                mSearchAdapter.refreshItems(response.body());
                mRecyclerView.setAdapter(mSearchAdapter);
                hideSoftKeyInput(mEdtSearch.getWindowToken());
            }

            @Override
            public void onFailure(Call<SearchBookPackage> call, Throwable t) {
                mRefreshView.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchAdapter.clear();
        mKeyWordAdapter.clear();
        mSearchDatas = null;
        mKeyWordDatas = null;
    }
}
