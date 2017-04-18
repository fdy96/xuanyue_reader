package com.example.fengdeyu.xuanyue_reader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.bean.HomeFindBean;

import java.util.List;

/**
 * Created by fengdeyu on 2016/11/30.
 */

public class HomeFindAdapter extends RecyclerView.Adapter<HomeFindAdapter.homeFindViewHolder> {

    private Context mContext;
    private List<HomeFindBean> mList;

    public HomeFindAdapter(Context mContext, List<HomeFindBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public interface onItemClickListener{
        void onItemClick(View view,int position);
    }
    private HomeFindAdapter.onItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(HomeFindAdapter.onItemClickListener mOnItemClickListener){
        this.mOnItemClickListener=mOnItemClickListener;
    }


    @Override
    public homeFindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.list_home_find,parent,false);

        return new homeFindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HomeFindAdapter.homeFindViewHolder holder, final int position) {
        holder.icon.setImageResource(mList.get(position).iconUrl);
        holder.itemName.setText(mList.get(position).itemName);

        if(mOnItemClickListener!=null) {

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

    public static class homeFindViewHolder extends RecyclerView.ViewHolder{
        public ImageView icon;
        public TextView itemName;

        public homeFindViewHolder(View itemView) {
            super(itemView);
            icon= (ImageView) itemView.findViewById(R.id.iv_icon);
            itemName= (TextView) itemView.findViewById(R.id.tv_item_name);
        }
    }
}
