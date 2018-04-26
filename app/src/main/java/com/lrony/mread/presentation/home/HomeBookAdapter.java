package com.lrony.mread.presentation.home;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lrony.mread.R;
import com.lrony.mread.data.bean.Status;
import com.lrony.mread.ui.help.GlideApp;
import com.lrony.mread.util.ImageLoader;

import java.util.List;

/**
 * Created by Lrony on 18-4-24.
 */
public class HomeBookAdapter extends BaseQuickAdapter<Status, BaseViewHolder> {

    private Context context;

    public HomeBookAdapter(@Nullable List<Status> data, Context context) {
        super(R.layout.item_grid_home_book, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Status item) {
        helper.setText(R.id.tv_title, item.getText());
        ImageLoader.load(context, "https://avatars1.githubusercontent.com/u/25895510?s=460&v=4"
                , (ImageView) helper.getView(R.id.iv_cover));
    }
}
