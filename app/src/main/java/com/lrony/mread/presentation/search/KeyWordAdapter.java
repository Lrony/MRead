package com.lrony.mread.presentation.search;

import android.content.Context;

import com.lrony.mread.R;
import com.lrony.mread.data.net.KeyWordPackage;
import com.lrony.mread.data.net.SearchBookPackage;
import com.lrony.mread.ui.help.BaseRecyclerAdapter;
import com.lrony.mread.ui.help.BaseViewHolder;

/**
 * Created by Lrony on 18-4-27.
 */
public class KeyWordAdapter extends BaseRecyclerAdapter<KeyWordPackage> {

    public KeyWordAdapter(Context context, KeyWordPackage data) {
        super(context, data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_search_key_word;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        holder.setText(R.id.tv_title, data.getKeywords().get(position));
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.getKeywords().size();
    }

    public void clear() {
        if (data != null) data.getKeywords().clear();
    }

    /**
     * 刷新数据
     *
     * @param data
     */
    public void refresh(KeyWordPackage data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
