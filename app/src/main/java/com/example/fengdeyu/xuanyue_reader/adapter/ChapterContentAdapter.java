package com.example.fengdeyu.xuanyue_reader.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.bean.ChapterContentBean;

import java.util.List;

/**
 * Created by fengdeyu on 2016/12/13.
 */

public class ChapterContentAdapter extends RecyclerView.Adapter<ChapterContentAdapter.chapterContentViewHolder> {

    private Context mContext;
    private List<ChapterContentBean> mList;
    private int currentChapter;

    public ChapterContentAdapter(Context mContext, List<ChapterContentBean> mList, int currentChapter) {
        this.mContext = mContext;
        this.mList = mList;

        this.currentChapter = currentChapter;
    }

    public interface onItemClickListener{
        void onItemClick(View view,int position);
    }
    private onItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(onItemClickListener mOnItemClickListener){
        this.mOnItemClickListener=mOnItemClickListener;
    }


    @Override
    public chapterContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.list_chapter_content,parent,false);
        return new chapterContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final chapterContentViewHolder holder, final int position) {
        holder.tv_chapter_name.setText(mList.get(position).chapter_name);
        if(position==currentChapter){
            holder.ic_chapter_content.setImageResource(R.mipmap.ic_chapter_content_activated);
        }

        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class chapterContentViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_chapter_name;
        public ImageView ic_chapter_content;

        public chapterContentViewHolder(View itemView) {
            super(itemView);
            tv_chapter_name= (TextView) itemView.findViewById(R.id.tv_chapter_name);
            ic_chapter_content= (ImageView) itemView.findViewById(R.id.ic_chapter_content);
        }
    }
}
