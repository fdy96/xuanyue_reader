package com.example.fengdeyu.xuanyue_reader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.activity.BookIntroActivity;
import com.example.fengdeyu.xuanyue_reader.activity.SearchActivity;
import com.example.fengdeyu.xuanyue_reader.activity.StartActivity;
import com.example.fengdeyu.xuanyue_reader.bean.BookItemBean;

import java.util.List;

/**
 * Created by fengdeyu on 2016/11/29.
 */

public class BookItemAdapter extends RecyclerView.Adapter<BookItemAdapter.bookItemViewHolder>{

    private Context mContext;
    private List<BookItemBean> mList;

    public BookItemAdapter(Context mContext, List<BookItemBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public bookItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_book_item,parent,false);

        return new bookItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookItemAdapter.bookItemViewHolder holder, int position) {

        holder.book_icon.setImageResource(R.mipmap.ic_launcher);
        holder.book_title.setText(mList.get(position).bookTitle);
        holder.book_author.setText(mList.get(position).bookAuthor);
        holder.book_content.setText(mList.get(position).bookcontent);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class bookItemViewHolder extends RecyclerView.ViewHolder{

        public ImageView book_icon;
        public TextView book_title;
        public TextView book_author;
        public TextView book_content;

        public bookItemViewHolder(View itemView) {
            super(itemView);
            book_icon= (ImageView) itemView.findViewById(R.id.iv_book_icon);
            book_title= (TextView) itemView.findViewById(R.id.tv_book_title);
            book_author= (TextView) itemView.findViewById(R.id.tv_book_author);
            book_content= (TextView) itemView.findViewById(R.id.tv_book_content);


        }
    }

}
