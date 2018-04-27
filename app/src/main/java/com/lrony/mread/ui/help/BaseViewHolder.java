package com.lrony.mread.ui.help;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lrony on 18-4-27.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private Map<Integer, View> mViewMap;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mViewMap = new HashMap<>();
    }

    /**
     * 获取设置的view
     *
     * @param id
     * @return
     */
    public <T extends View> T getView(int id) {
        View view = mViewMap.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            mViewMap.put(id, view);
        }
        return (T) view;
    }

    public BaseViewHolder setText(@IdRes int viewId, CharSequence value) {
        TextView view = getView(viewId);
        view.setText(value);
        return this;
    }
}
