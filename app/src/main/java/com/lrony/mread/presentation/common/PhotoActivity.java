package com.lrony.mread.presentation.common;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.chrisbanes.photoview.PhotoView;
import com.lrony.mread.R;
import com.lrony.mread.presentation.search.SearchActivity;
import com.lrony.mread.ui.help.GlideApp;
import com.lrony.mread.ui.help.ToolbarHelper;
import com.lrony.mread.util.ImageLoader;

public class PhotoActivity extends AppCompatActivity {

    private static final String TAG = "PhotoActivity";
    private static final String K_IMAGES_TITLE = "title";
    private static final String K_IMAGES_PATH = "path";

    private String mTitle, mPath;

    private PhotoView mPhotoView;

    public static Intent newIntent(Context context, String title, String path) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra(K_IMAGES_TITLE, title);
        intent.putExtra(K_IMAGES_PATH, path);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_photo);

        initView();
    }

    private void init() {
        mTitle = getIntent().getStringExtra(K_IMAGES_TITLE);
        mPath = getIntent().getStringExtra(K_IMAGES_PATH);
        Log.d(TAG, "init mTitle = " + mTitle + ",mPath = " + mPath);
    }

    private void initView() {
        ToolbarHelper.initToolbar(this, R.id.toolbar, true, mTitle);
        mPhotoView = findViewById(R.id.photoView);

        ImageLoader.load(this, mPath, mPhotoView);
    }
}
