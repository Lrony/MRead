package com.lrony.mread.presentation.book.catalog;

import android.content.Context;

import com.lrony.mread.R;
import com.lrony.mread.data.net.BookChapterPackage;
import com.lrony.mread.ui.help.BaseRecyclerAdapter;
import com.lrony.mread.ui.help.BaseViewHolder;

/**
 * Created by Lrony on 18-5-10.
 */
public class BookCatalogAdapter extends BaseRecyclerAdapter<BookChapterPackage> {

    public BookCatalogAdapter(Context context, BookChapterPackage data) {
        super(context, data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_book_section;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        holder.setText(R.id.tv_section_name, data.getMixToc().getChapters().get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.getMixToc().getChapters().size();
    }
}
