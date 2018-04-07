package com.lrony.mread.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lrony.mread.R;
import com.lrony.mread.data.bean.SortBookPackage;
import com.lrony.mread.util.Constant;
import com.squareup.picasso.Picasso;

/**
 * Created by lrony on 2018/4/7.
 */
public class SearchTypeAdapter extends RecyclerView.Adapter<SearchTypeAdapter.ViewHolder> {

    private Context context;
    private SortBookPackage datas;

    public SearchTypeAdapter(Context context, SortBookPackage datas) {
        this.context = context;
        this.datas = datas;
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
        View root = LayoutInflater.from(context).inflate(R.layout.item_list_search_book
                , parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.name.setText(datas.getBooks().get(position).getTitle());
        holder.shortIntro.setText(datas.getBooks().get(position).getShortIntro());
        holder.author.setText(datas.getBooks().get(position).getAuthor());
        holder.minorCate.setText(datas.getBooks().get(position).getMinorCate());

        Picasso.get()
                .load(Constant.IMG_BASE_URL + datas.getBooks().get(position).getCover())
                .resize(102, 64)
                .into(holder.photo);

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
        if (datas != null) {
            return datas.getBooks().size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView photo;
        private TextView name;
        private TextView shortIntro;
        private TextView author;
        private TextView minorCate;

        public ViewHolder(View itemView) {
            super(itemView);

            photo = itemView.findViewById(R.id.img_photo);
            name = itemView.findViewById(R.id.tv_name);
            shortIntro = itemView.findViewById(R.id.tv_shortIntro);
            author = itemView.findViewById(R.id.tv_author);
            minorCate = itemView.findViewById(R.id.tv_minorCate);
        }
    }

    public void refreshItems(SortBookPackage data) {
        datas = data;
        notifyDataSetChanged();
    }
}
