package com.example.fengdeyu.xuanyue_reader.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.fengdeyu.xuanyue_reader.R;

import java.util.ArrayList;

/**
 * Created by fengdeyu on 2017/1/7.
 */

public class PageViewAdapter extends PageAdapter {

    Context mContext;
    ArrayList<String> mList;
    AssetManager am;

    public PageViewAdapter(Context mContext,ArrayList<String> mList){
        this.mContext=mContext;
        this.mList=mList;
        am=mContext.getAssets();
    }



    @Override
    public View getView() {
        View view= LayoutInflater.from(mContext).inflate(R.layout.page_layout,null);
        return view;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public void addContent(View view, int position) {

        TextView tv_contents = (TextView) view.findViewById(R.id.tv_contents);
        if ((position - 1) < 0 || (position - 1) >= getCount())
            return;
        tv_contents.setText(mList.get(position - 1));
    }
}
