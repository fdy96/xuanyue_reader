package com.example.fengdeyu.xuanyue_reader.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.adapter.BookcaseAdapter;
import com.example.fengdeyu.xuanyue_reader.bean.BookItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengdeyu on 2016/11/23.
 */

public class BookcaseFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<BookItemBean> mList=new ArrayList<>();




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecyclerView= (RecyclerView) inflater.inflate(R.layout.bookcase_fragment,container,false);
        return mRecyclerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Bundle bundle=getArguments();

        loadList();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

        mRecyclerView.setAdapter(new BookcaseAdapter(getActivity(),mList));
    }

    public void loadList(){//加载书架数据
        BookItemBean bookItemBean1=new BookItemBean();
        bookItemBean1.bookTitle="仙逆";
        bookItemBean1.bookAuthor="作者:耳根";
        bookItemBean1.bookcontent="第二百一十四章 陈绍轩";

        mList.add(bookItemBean1);

        BookItemBean bookItemBean2=new BookItemBean();
        bookItemBean2.bookTitle="太浩";
        bookItemBean2.bookAuthor="作者:无极书虫";
        bookItemBean2.bookcontent="第二百一十五章 我是你爸爸啊";

        mList.add(bookItemBean2);
    }

}
