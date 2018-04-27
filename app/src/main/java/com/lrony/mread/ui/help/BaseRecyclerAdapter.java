package com.lrony.mread.ui.help;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lrony on 18-4-27.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder> {

    public Context context;
    public T data;

    public BaseRecyclerAdapter(Context context, T data) {
        this.context = context;
        this.data = data;
    }

    /**
     * 获取子item
     *
     * @return
     */
    public abstract int getLayoutId();

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerAdapter.BaseViewHolder holder, final int position) {

        bindData(holder, position);

    }

    /**
     * 绑定数据
     *
     * @param holder   具体的viewHolder
     * @param position 对应的索引
     */
    protected abstract void bindData(BaseViewHolder holder, int position);

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
}
