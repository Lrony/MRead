package com.lrony.mread.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lrony.mread.R;
import com.lrony.mread.data.bean.RecommendBooksPackage;
import com.lrony.mread.ui.widget.MaskableImageView;
import com.lrony.mread.util.Constant;
import com.squareup.picasso.Picasso;

/**
 * Created by Lrony on 18-4-8.
 */
public class RecommendBooksAdapter extends RecyclerView.Adapter<RecommendBooksAdapter.ViewHolder> {

    private Context context;
    private RecommendBooksPackage datas;
    private int showCount;

    public RecommendBooksAdapter(Context context, RecommendBooksPackage datas, int showCount) {
        this.context = context;
        this.datas = datas;
        this.showCount = showCount;
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
        View root = LayoutInflater.from(context).inflate(R.layout.grid_item_bookcase_book, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.title.setText(datas.getBooks().get(position).getTitle());
        Picasso.get()
                .load(Constant.IMG_BASE_URL + datas.getBooks().get(position).getCover())
                .resize(90, 120)
                .into(holder.photo);

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
            holder.photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.photo, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (datas != null) {
            if (datas.getBooks().size() >= showCount) {
                return showCount;
            }
            return datas.getBooks().size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private MaskableImageView photo;
        private TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.iv_cover);
            title = itemView.findViewById(R.id.tv_title);
        }
    }

    public void refreshItems(RecommendBooksPackage data) {
        datas = data;
        notifyDataSetChanged();
    }
}
