package com.lrony.mread.ui.help;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lrony on 18-4-27.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    public Context context;
    public T data;

    public BaseRecyclerAdapter(Context context, T data) {
        this.context = context;
        this.data = data;
    }

    private OnItemClickListener mItemClickListener;

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemLongClick((Integer) v.getTag());
        }
        return true;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onItemLongClick(int position);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    /**
     * 刷新数据
     * @param data
     */
    public void refresh(T data) {
        this.data = data;
        notifyDataSetChanged();
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
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        bindData(holder, position);
        holder.itemView.setTag(position);
    }

    /**
     * 绑定数据
     *
     * @param holder   具体的viewHolder
     * @param position 对应的索引
     */
    protected abstract void bindData(BaseViewHolder holder, int position);
}
