package com.example.fengdeyu.xuanyue_reader.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.activity.ReadActivity;
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

        BookcaseAdapter bookcaseAdapter=new BookcaseAdapter(getActivity(),mList);

        mRecyclerView.setAdapter(bookcaseAdapter);

        bookcaseAdapter.setOnItemClickListener(new BookcaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(),mList.get(position).bookTitle.toString(),Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), ReadActivity.class));
            }
        });

    }

    public void loadList(){//加载书架数据
        BookItemBean bookItemBean1=new BookItemBean();
        bookItemBean1.bookIconUrl=R.mipmap.book_icon2;
        bookItemBean1.bookTitle="仙逆";
        bookItemBean1.bookAuthor="作者:耳根";
        bookItemBean1.bookcontent="第2088章 蓦然回首（结局）";

        mList.add(bookItemBean1);

        BookItemBean bookItemBean2=new BookItemBean();
        bookItemBean2.bookIconUrl=R.mipmap.book_icon;
        bookItemBean2.bookTitle="太浩";
        bookItemBean2.bookAuthor="作者:无极书虫";
        bookItemBean2.bookcontent="同人:没有梦蝶的世界";

        mList.add(bookItemBean2);

        BookItemBean bookItemBean3=new BookItemBean();
        bookItemBean3.bookIconUrl=R.mipmap.book_icon1;
        bookItemBean3.bookTitle="岁月是朵两生花";
        bookItemBean3.bookAuthor="作者:唐七公子";
        bookItemBean3.bookcontent="第二十六章 两生花";

        mList.add(bookItemBean3);
    }

}
