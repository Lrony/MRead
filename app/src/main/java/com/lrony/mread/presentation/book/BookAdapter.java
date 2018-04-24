package com.lrony.mread.presentation.book;

import com.lrony.mread.R;
import com.lrony.mread.data.bean.Book;
import com.lrony.mread.ui.help.CommonAdapter;
import com.lrony.mread.ui.help.CommonViewHolder;

import java.util.List;

/**
 * Created by Lrony on 18-4-24.
 */
public class BookAdapter extends CommonAdapter<Book> {

    public BookAdapter(List<Book> data) {
        super(R.layout.item_book, data);
    }

    @Override
    protected void convert(CommonViewHolder helper, Book item) {

    }
}
