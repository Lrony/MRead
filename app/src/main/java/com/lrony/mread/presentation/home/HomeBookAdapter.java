package com.lrony.mread.presentation.home;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lrony.mread.R;
import com.lrony.mread.data.bean.Status;

import java.util.List;

/**
 * Created by Lrony on 18-4-24.
 */
public class HomeBookAdapter extends BaseQuickAdapter<Status, BaseViewHolder> {

    public HomeBookAdapter(@Nullable List<Status> data) {
        super(R.layout.item_grid_home_book, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Status item) {
        helper.setText(R.id.tv_title, item.getText());
    }
}
