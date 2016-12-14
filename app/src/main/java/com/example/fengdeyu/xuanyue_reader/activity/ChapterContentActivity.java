package com.example.fengdeyu.xuanyue_reader.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.adapter.ChapterContentAdapter;
import com.example.fengdeyu.xuanyue_reader.bean.ChapterContentBean;
import com.example.fengdeyu.xuanyue_reader.other.GetChapterContent;

import java.util.ArrayList;
import java.util.List;

public class ChapterContentActivity extends Activity {
    private RecyclerView chapter_content_recycler_view;
    private TextView tv_book_title;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_content);


        chapter_content_recycler_view= (RecyclerView) findViewById(R.id.chapter_content_recycler_view);
        tv_book_title= (TextView) findViewById(R.id.tv_book_title);
        tv_book_title.setText(GetChapterContent.getInstance().bookTitle);

        ChapterContentAdapter adapter=new ChapterContentAdapter(ChapterContentActivity.this,GetChapterContent.getInstance().mList);
        chapter_content_recycler_view.setLayoutManager(new LinearLayoutManager(chapter_content_recycler_view.getContext()));
        chapter_content_recycler_view.setAdapter(adapter);
        chapter_content_recycler_view.scrollToPosition(GetChapterContent.getInstance().currentChapter);


        adapter.setOnItemClickListener(new ChapterContentAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                GetChapterContent.getInstance().currentChapter=position;
                onBackPressed();

            }
        });



    }


}
