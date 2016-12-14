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
    private int clickPos;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecyclerView= (RecyclerView) inflater.inflate(R.layout.bookcase_fragment,container,false);
        return mRecyclerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

        bookcaseAdapter=new BookcaseAdapter(getActivity(), GetBookCase.getInstance().mList);


        mRecyclerView.setAdapter(bookcaseAdapter);

        bookcaseAdapter.setOnItemClickListener(new BookcaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                clickPos=position;
                new loadChapterContentAsyncTask().execute(GetBookCase.getInstance().mList.get(position).bookHref);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        bookcaseAdapter.notifyDataSetChanged();
    }

    public class loadChapterContentAsyncTask extends AsyncTask<String,Void,Void> {

        @Override
        protected Void doInBackground(String... params) {
            GetChapterContent.getInstance().loadChapterContent(params[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            GetChapterContent.getInstance().currentChapter=GetBookCase.getInstance().mList.get(clickPos).currentChapter;
            GetChapterContent.getInstance().bookTitle=GetBookCase.getInstance().mList.get(clickPos).bookTitle;
            startActivity(new Intent(getActivity(), ReadActivity.class).putExtra("bookId",clickPos).putExtra("intoWay","bookCase"));

        }
    }
}
