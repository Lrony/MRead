package com.lrony.mread.presentation.book.recommend;

import android.content.Context;
import android.widget.ImageView;

import com.lrony.mread.R;
import com.lrony.mread.data.net.RecommendBooksPackage;
import com.lrony.mread.ui.help.BaseRecyclerAdapter;
import com.lrony.mread.ui.help.BaseViewHolder;
import com.lrony.mread.util.Constant;
import com.lrony.mread.util.ImageLoader;
import com.lrony.mread.util.StringUtils;

/**
 * Created by Lrony on 18-4-28.
 */
public class RecommendAdapter extends BaseRecyclerAdapter<RecommendBooksPackage> {

    public RecommendAdapter(Context context, RecommendBooksPackage data) {
        super(context, data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_book;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        holder.setText(R.id.tv_title, data.getBooks().get(position).getTitle());
        holder.setText(R.id.tv_describe, data.getBooks().get(position).getShortIntro());
        holder.setText(R.id.tv_author, data.getBooks().get(position).getAuthor());
        holder.setText(R.id.tv_type, data.getBooks().get(position).getMinorCate());
        holder.setText(R.id.tv_retention_ratio, data.getBooks().get(position).getRetentionRatio()
                + StringUtils.getStringByID(R.string.book_retention_ratio));

        ImageLoader.load(context, Constant.IMG_BASE_URL + data.getBooks().get(position).getCover()
                , (ImageView) holder.getView(R.id.iv_cover));
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.getBooks().size();
    }
}
