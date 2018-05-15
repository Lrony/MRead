package com.lrony.mread.presentation.read;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.lrony.mread.R;
import com.lrony.mread.data.net.BookChapterPackage;
import com.lrony.mread.ui.help.BaseRecyclerAdapter;
import com.lrony.mread.ui.help.BaseViewHolder;

/**
 * Created by Lrony on 18-5-15.
 */
public class ReadSectionAdapter extends BaseRecyclerAdapter<BookChapterPackage> {

    private int textColor = -1;

    public ReadSectionAdapter(Context context, BookChapterPackage data) {
        super(context, data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_book_section;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        TextView textView = holder.getView(R.id.tv_section_name);
        textView.setText(data.getMixToc().getChapters().get(position).getTitle());
        if (textColor == -1) {
            textView.setTextColor(ContextCompat.getColor(context, R.color.textPrimary));
        } else {
            textView.setTextColor(textColor);
        }
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.getMixToc().getChapters().size();
    }

    public void setTextColor(int color) {
        textColor = color;
        notifyDataSetChanged();
    }
}
