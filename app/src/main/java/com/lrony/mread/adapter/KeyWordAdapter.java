package com.lrony.mread.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lrony.mread.R;
import com.lrony.mread.data.bean.KeyWordPackage;

import java.util.ArrayList;

/**
 * Created by lrony on 2018/4/7.
 */
public class KeyWordAdapter extends RecyclerView.Adapter<KeyWordAdapter.ViewHolder> {

    private Context context;
    private KeyWordPackage keyWords;

    public KeyWordAdapter(Context context, KeyWordPackage keyWord) {
        this.context = context;
        this.keyWords = keyWord;
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_list_key_word
                , parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.title.setText(keyWords.getKeywords().get(position));

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return keyWords.getKeywords().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
        }
    }

    public void clear() {
        if (keyWords != null) keyWords.getKeywords().clear();
    }

    public void refreshItems(KeyWordPackage data) {
        keyWords = data;
        notifyDataSetChanged();
    }
}
