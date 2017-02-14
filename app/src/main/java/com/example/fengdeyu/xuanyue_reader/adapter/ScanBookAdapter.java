package com.example.fengdeyu.xuanyue_reader.adapter;

import android.content.Context;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.bean.ScanBookBean;

import java.util.List;

/**
 * Created by fengdeyu on 2016/12/29.
 */

public class ScanBookAdapter extends RecyclerView.Adapter<ScanBookAdapter.scanBookViewHolder> {
    private Context mContext;
    public List<ScanBookBean> mList;

    public ScanBookAdapter(Context mContext, List<ScanBookBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public interface onItemClickListener{
        void onItemClick(View view,int position);
    }
    private onItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(onItemClickListener mOnItemClickListener){
        this.mOnItemClickListener=mOnItemClickListener;
    }


    @Override
    public scanBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.list_scan_book,parent,false);

        return new scanBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final scanBookViewHolder holder, final int position) {
//        if(mList.get(position).isSelect){
//            holder.select.setImageResource(R.mipmap.ic_checkbox_selected);
//        }
        if(mList.get(position).isSelect==true){
            holder.select.setImageResource(R.mipmap.ic_checkbox_selected);
        }else {
            holder.select.setImageResource(R.mipmap.ic_checkbox_normal);
        }
        holder.fileName.setText(mList.get(position).fileName);
        holder.fileSize.setText(mList.get(position).fileSize);

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
//
        Log.i("mList.size",""+mList.size());
        return mList.size();
    }

    public static class scanBookViewHolder extends RecyclerView.ViewHolder{

        public TextView fileName;
        public TextView fileSize;
        public ImageView select;

        public scanBookViewHolder(View itemView) {
            super(itemView);

            fileName= (TextView) itemView.findViewById(R.id.tv_file_name);
            fileSize= (TextView) itemView.findViewById(R.id.tv_file_size);
            select= (ImageView) itemView.findViewById(R.id.iv_select);


        }
    }
}
