package com.lrony.mread.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lrony.mread.R;
import com.lrony.mread.ui.help.GlideApp;

/**
 * Created by Lrony on 18-4-26.
 * 图片加载类,统一适配(方便换库,方便管理)
 */
public class ImageLoader {

    public static void load(Context context, String image, ImageView view) {
        GlideApp
                .with(context)
                .load(image)
                .placeholder(R.drawable.ic_book_cover_default)
                .into(view);
    }

    public static void clear(Context context) {

        Glide.getPhotoCacheDir(context).delete();
    }
}
