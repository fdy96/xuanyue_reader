package com.example.fengdeyu.xuanyue_reader.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.activity.FindCategoryActivity;
import com.example.fengdeyu.xuanyue_reader.activity.FindRanklistActivity;
import com.example.fengdeyu.xuanyue_reader.adapter.HomeFindAdapter;
import com.example.fengdeyu.xuanyue_reader.bean.HomeFindBean;
import com.example.fengdeyu.xuanyue_reader.other.ConnectTestActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by fengdeyu on 2016/12/1.
 */

public class HomeFindFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<HomeFindBean> mList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecyclerView= (RecyclerView) inflater.inflate(R.layout.home_find_fragment,container,false);
        return mRecyclerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadList();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        HomeFindAdapter adapter=new HomeFindAdapter(getActivity(),mList);

        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new HomeFindAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("info",position+"");
                switch (position){
                    case 0:
                        startActivity(new Intent(getActivity(),FindCategoryActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(),FindRanklistActivity.class));
                        break;
                }
            }
        });



    }

    private void loadList() {
        mList.clear();
        HomeFindBean homeFindBean1=new HomeFindBean();
        homeFindBean1.iconUrl=R.mipmap.home_find_category;
        homeFindBean1.itemName="分类";
        mList.add(homeFindBean1);
        HomeFindBean homeFindBean2=new HomeFindBean();
        homeFindBean2.iconUrl=R.mipmap.home_find_rank;
        homeFindBean2.itemName="排行榜";
        mList.add(homeFindBean2);
        HomeFindBean homeFindBean3=new HomeFindBean();
        homeFindBean3.iconUrl=R.mipmap.ic_lock;
        homeFindBean3.itemName="听书";
        mList.add(homeFindBean3);
        HomeFindBean homeFindBean4=new HomeFindBean();
        homeFindBean4.iconUrl=R.mipmap.ic_lock;
        homeFindBean4.itemName="主题书单";
        mList.add(homeFindBean4);

    }
}
