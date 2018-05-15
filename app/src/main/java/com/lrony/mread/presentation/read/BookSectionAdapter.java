package com.lrony.mread.presentation.read;

import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.lrony.mread.R;
import com.lrony.mread.data.bean.BookSectionItem;
import com.lrony.mread.ui.help.CommonAdapter;
import com.lrony.mread.ui.help.CommonViewHolder;

import java.util.List;

/**
 * Created by Lrony on 18-5-15.
 */
public class BookSectionAdapter extends CommonAdapter<BookSectionItem> {

    private int textColor = -1;

    public BookSectionAdapter(List<BookSectionItem> data) {
        super(R.layout.item_book_section, data);
    }

    @Override
    protected void convert(CommonViewHolder helper, BookSectionItem item) {
        TextView textView = helper.getView(R.id.tv_section_name);
        textView.setText(item.getSectionName());
        if (textColor == -1) {
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.textPrimary));
        } else {
            textView.setTextColor(textColor);
        }
    }

    public void setTextColor(int color) {
        textColor = color;
        notifyDataSetChanged();
    }
}
