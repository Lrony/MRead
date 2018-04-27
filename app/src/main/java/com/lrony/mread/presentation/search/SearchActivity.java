package com.lrony.mread.presentation.search;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lrony.mread.R;
import com.lrony.mread.data.net.KeyWordPackage;
import com.lrony.mread.data.net.SearchBookPackage;
import com.lrony.mread.mvp.MvpActivity;
import com.lrony.mread.presentation.book.BookDetailActivity;
import com.lrony.mread.ui.help.RecyclerViewItemDecoration;
import com.lrony.mread.util.InputMethodUtils;

public class SearchActivity extends MvpActivity<SearchContract.Presenter> implements SearchContract.View, View.OnClickListener {

    private static final String TAG = "SearchActivity";

    private static final String K_KEYWORD = "search";

    private String mDefaultSearch;

    private ImageView mIvArrowBack;
    private ImageView mIvActionSearch;
    private ImageView mIvActionClear;
    private EditText mEtSearch;
    private RecyclerView mRecyclerView;

    private SearchBookPackage mSearchData;
    private KeyWordPackage mKeyWordData;

    private SearchBookAdapter mSearchAdapter;
    private KeyWordAdapter mKeyWordAdapter;

    public static Intent newIntent(Context context, String str) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(K_KEYWORD, str);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_search);
        getPresenter().start();

        initView();
        initListener();
    }

    @NonNull
    @Override
    public SearchContract.Presenter createPresenter() {
        return new SearchPresenter();
    }

    private void init() {
        mDefaultSearch = getIntent().getStringExtra(K_KEYWORD);
    }

    private void initView() {
        mIvArrowBack = findViewById(R.id.iv_arrow_back);
        mIvActionSearch = findViewById(R.id.iv_action_search);
        mIvActionClear = findViewById(R.id.iv_action_clear);
        mEtSearch = findViewById(R.id.et_search);
        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.colorDivider))
                .thickness(1)
                .create());

        if (mDefaultSearch != null) {
            mEtSearch.setText(mDefaultSearch);
            mEtSearch.setSelection(mDefaultSearch.length());
        }

        mSearchAdapter = new SearchBookAdapter(SearchActivity.this, mSearchData);
        mKeyWordAdapter = new KeyWordAdapter(SearchActivity.this, mKeyWordData);
    }

    private void initListener() {
        bindOnClickLister(this, mIvActionSearch, mIvArrowBack, mIvActionClear);

        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    goSearchResult();
                    return true;
                }
                return false;
            }
        });
        mEtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && mEtSearch.getText().length() > 0) {
                    mIvActionClear.setVisibility(View.VISIBLE);
                } else {
                    mIvActionClear.setVisibility(View.GONE);
                }
            }
        });
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0 && mEtSearch.isFocusable()) {
                    mIvActionClear.setVisibility(View.VISIBLE);
                } else {
                    mIvActionClear.setVisibility(View.GONE);
                }
                getKeyWords();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEtSearch.post(new Runnable() {
            @Override
            public void run() {
                InputMethodUtils.showSoftInput(mEtSearch);
            }
        });
    }

    private void goSearchResult() {
        Log.d(TAG, "goSearchResult");
        Editable etSearchText = mEtSearch.getText();
        if (etSearchText != null) {
            String trim = etSearchText.toString().trim();
            Log.d(TAG, "goSearchResult trim = " + trim);
            if (trim.length() > 0) {
                getPresenter().loadSearchBook(trim);
            }
        }
    }

    private void getKeyWords() {
        Log.d(TAG, "getKeyWords");
        Editable etSearchText = mEtSearch.getText();
        if (etSearchText != null) {
            String trim = etSearchText.toString().trim();
            Log.d(TAG, "getKeyWords trim = " + trim);
            if (trim.length() > 0) {
                getPresenter().loadKeyWord(trim);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_back:
                onBackPressed();
                InputMethodUtils.hideSoftInput(this);
                break;
            case R.id.iv_action_search:
                goSearchResult();
                break;
            case R.id.iv_action_clear:
                mEtSearch.setText("");
                break;
        }
    }

    @Override
    public void showKeyWord(KeyWordPackage keywords) {
        mKeyWordAdapter.clear();
        mSearchAdapter.clear();
        mRecyclerView.removeAllViews();

        mRecyclerView.setAdapter(mKeyWordAdapter);
        mKeyWordAdapter.refresh(keywords);
    }

    @Override
    public void showSearchBook(SearchBookPackage books) {
        mKeyWordAdapter.clear();
        mSearchAdapter.clear();
        mRecyclerView.removeAllViews();

        mRecyclerView.setAdapter(mSearchAdapter);
        mSearchAdapter.refresh(books);
    }
}
