package com.lrony.mread.presentation.book;

import android.content.Context;
import android.widget.ImageView;

import com.lrony.mread.R;
import com.lrony.mread.data.net.RecommendBooksPackage;
import com.lrony.mread.ui.help.BaseRecyclerAdapter;
import com.lrony.mread.ui.help.BaseViewHolder;
import com.lrony.mread.util.Constant;
import com.lrony.mread.util.ImageLoader;

/**
 * Created by Lrony on 18-4-27.
 */
public class RecommendBookAdapter extends BaseRecyclerAdapter<RecommendBooksPackage> {

    private int showCount;

    public RecommendBookAdapter(Context context, RecommendBooksPackage data, int showCount) {
        super(context, data);
        this.showCount = showCount;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_grid_home_book;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        holder.setText(R.id.tv_title, data.getBooks().get(position).getTitle());
        ImageLoader.load(context, Constant.IMG_BASE_URL + data.getBooks().get(position).getCover()
                , (ImageView) holder.getView(R.id.iv_cover));
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }

        if (data.getBooks().size() >= showCount) {
            return showCount;
        }
        return data.getBooks().size();
    }

    /**
     * 刷新数据
     *
     * @param data
     */
    public void refresh(RecommendBooksPackage data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
