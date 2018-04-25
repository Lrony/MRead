package com.lrony.mread.presentation.book;

import android.content.Context;
import android.widget.ImageView;

import com.lrony.mread.App;
import com.lrony.mread.R;
import com.lrony.mread.data.bean.Book;
import com.lrony.mread.ui.help.CommonAdapter;
import com.lrony.mread.ui.help.CommonViewHolder;
import com.lrony.mread.ui.help.GlideApp;
import com.lrony.mread.util.Constant;
import com.lrony.mread.util.StringUtils;

import java.util.List;

/**
 * Created by Lrony on 18-4-24.
 */
public class BookAdapter extends CommonAdapter<Book> {

    private Context context;

    public BookAdapter(List<Book> data, Context context) {
        super(R.layout.item_book, data);
        this.context = context;
    }

    @Override
    protected void convert(CommonViewHolder helper, Book item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_describe, item.getShortIntro());
        helper.setText(R.id.tv_author, item.getAuthor());
        helper.setText(R.id.tv_type, item.getMinorCate());
        helper.setText(R.id.tv_retention_ratio, item.getRetentionRatio()
                + StringUtils.getStringByID(R.string.book_retention_ratio));

        GlideApp.with(context)
                .load(Constant.IMG_BASE_URL + item.getCover())
                .placeholder(R.drawable.ic_book_cover_default)
                .into((ImageView) helper.getView(R.id.iv_cover));
    }
}
