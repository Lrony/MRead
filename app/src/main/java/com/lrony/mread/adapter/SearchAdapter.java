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
import com.lrony.mread.data.bean.SearchBookPackage;
import com.lrony.mread.util.Constant;
import com.squareup.picasso.Picasso;

/**
 * Created by lrony on 2018/4/7.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Context context;
    private SearchBookPackage datas;

    public SearchAdapter(Context context, SearchBookPackage datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_list_search_book
                , parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(datas.getBooks().get(position).getTitle());
        holder.shortIntro.setText(datas.getBooks().get(position).getShortIntro());
        holder.author.setText(datas.getBooks().get(position).getAuthor());

        Picasso.get()
                .load(Constant.IMG_BASE_URL + datas.getBooks().get(position).getCover())
                .resize(102, 64)
                .into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return datas.getBooks().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView photo;
        private TextView name;
        private TextView shortIntro;
        private TextView author;

        public ViewHolder(View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.img_photo);
            name = itemView.findViewById(R.id.tv_name);
            shortIntro = itemView.findViewById(R.id.tv_shortIntro);
            author = itemView.findViewById(R.id.tv_author);
        }
    }

    public void clear() {
        if (datas != null) datas.getBooks().clear();
    }

    public void refreshItems(SearchBookPackage data) {
        datas = data;
        notifyDataSetChanged();
    }
}
