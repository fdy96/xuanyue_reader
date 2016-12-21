package com.example.fengdeyu.xuanyue_reader.fragment;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.fengdeyu.xuanyue_reader.activity.BookIntroActivity;
import com.example.fengdeyu.xuanyue_reader.activity.ReadActivity;
import com.example.fengdeyu.xuanyue_reader.adapter.BookcaseAdapter;
import com.example.fengdeyu.xuanyue_reader.bean.BookItemBean;
import com.example.fengdeyu.xuanyue_reader.other.GetBookCase;
import com.example.fengdeyu.xuanyue_reader.other.GetChapterContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengdeyu on 2016/11/23.
 */

public class BookcaseFragment extends Fragment {
    private RecyclerView mRecyclerView;
    BookcaseAdapter bookcaseAdapter;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecyclerView= (RecyclerView) inflater.inflate(R.layout.bookcase_fragment,container,false);
        return mRecyclerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadTestBook();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

        bookcaseAdapter=new BookcaseAdapter(getActivity(), GetBookCase.getInstance().mList);


        mRecyclerView.setAdapter(bookcaseAdapter);

        bookcaseAdapter.setOnItemClickListener(new BookcaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                GetChapterContent.getInstance().currentChapter=GetBookCase.getInstance().mList.get(position).currentChapter;
                GetChapterContent.getInstance().bookTitle=GetBookCase.getInstance().mList.get(position).bookTitle;
                startActivity(new Intent(getActivity(), ReadActivity.class).putExtra("bookId",position).putExtra("intoWay","bookCase"));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        bookcaseAdapter.notifyDataSetChanged();



    }

    private void loadTestBook(){
        final BookItemBean bookItemBean=new BookItemBean();
        bookItemBean.bookTitle="仙逆";
        bookItemBean.bookAuthor="作者:  耳根";
        bookItemBean.bookContent="最新章节:新书--我欲封天";
        bookItemBean.bookHref="http://www.23us.so/files/article/html/10/10674/index.html";
        bookItemBean.bookIconUrl="http://www.23us.so/files/article/image/10/10674/10674s.jpg";
        if(!GetBookCase.getInstance().hasBook(bookItemBean)){
            GetBookCase.getInstance().mList.add(bookItemBean);

            new Thread(){
                @Override
                public void run() {
                    super.run();
                    GetBookCase.getInstance().loadChapterContent(0,bookItemBean.bookHref);
                }
            }.start();
        }

    }
}
