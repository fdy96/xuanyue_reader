package com.example.fengdeyu.xuanyue_reader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.bean.BookItemBean;

import java.util.List;

/**
 * Created by fengdeyu on 2016/11/23.
 */

public class BookcaseAdapter extends RecyclerView.Adapter<BookcaseAdapter.bookcaseViewHolder> {

    private Context mContext;
    private List<BookItemBean> mList;


    public BookcaseAdapter(Context mContext, List<BookItemBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @Override
    public BookcaseAdapter.bookcaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.list_book_item_card,parent,false);
        return new bookcaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookcaseAdapter.bookcaseViewHolder holder, int position) {
        holder.book_icon.setImageResource(R.mipmap.ic_launcher);
        holder.book_title.setText(mList.get(position).bookTitle);
        holder.book_author.setText(mList.get(position).bookAuthor);
        holder.book_content.setText(mList.get(position).bookcontent);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class bookcaseViewHolder extends RecyclerView.ViewHolder{

        public ImageView book_icon;
        public TextView book_title;
        public TextView book_author;
        public TextView book_content;

        public bookcaseViewHolder(View itemView) {
            super(itemView);
            book_icon= (ImageView) itemView.findViewById(R.id.iv_book_icon);
            book_title= (TextView) itemView.findViewById(R.id.tv_book_title);
            book_author= (TextView) itemView.findViewById(R.id.tv_book_author);
            book_content= (TextView) itemView.findViewById(R.id.tv_book_content);
        }
    }
}
