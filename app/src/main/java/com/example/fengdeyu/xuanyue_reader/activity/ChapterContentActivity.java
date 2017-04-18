package com.example.fengdeyu.xuanyue_reader.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.adapter.ChapterContentAdapter;
import com.example.fengdeyu.xuanyue_reader.bean.ChapterContentBean;
import com.example.fengdeyu.xuanyue_reader.other.GetBookCase;
import com.example.fengdeyu.xuanyue_reader.other.GetChapterContent;
import com.example.fengdeyu.xuanyue_reader.other.GetPageAttribute;

import java.util.ArrayList;
import java.util.List;

public class ChapterContentActivity extends Activity {
    private RecyclerView chapter_content_recycler_view;
    private TextView tv_book_title;
    private TextView tv_top_bottom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_content);


        final ChapterContentAdapter adapter;

        chapter_content_recycler_view= (RecyclerView) findViewById(R.id.chapter_content_recycler_view);
        tv_book_title= (TextView) findViewById(R.id.tv_book_title);
        if(getIntent().getStringExtra("intoWay").equals("bookCase")){

            tv_book_title.setText(GetBookCase.getInstance().mList.get(getIntent().getIntExtra("bookId",0)).bookTitle);
            adapter=new ChapterContentAdapter(ChapterContentActivity.this,
                    GetBookCase.getInstance().mList.get(getIntent().getIntExtra("bookId",0)).mChapterList);
        }else {
            tv_book_title.setText(GetChapterContent.getInstance().bookTitle);
            adapter=new ChapterContentAdapter(ChapterContentActivity.this,
                    GetChapterContent.getInstance().mList);

        }

        tv_top_bottom= (TextView) findViewById(R.id.tv_top_bottom);
        tv_top_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tv_top_bottom.getText().toString().equals("到底部")){
                    chapter_content_recycler_view.scrollToPosition(adapter.getItemCount()-1);
                    tv_top_bottom.setText("到顶部");
                }else {
                    chapter_content_recycler_view.scrollToPosition(0);
                    tv_top_bottom.setText("到底部");
                }


            }
        });


        chapter_content_recycler_view.setLayoutManager(new LinearLayoutManager(chapter_content_recycler_view.getContext()));
        chapter_content_recycler_view.setAdapter(adapter);
        chapter_content_recycler_view.scrollToPosition(GetChapterContent.getInstance().currentChapter);


        adapter.setOnItemClickListener(new ChapterContentAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                GetChapterContent.getInstance().currentChapter=position;
                if(GetPageAttribute.getInstance().source.equals("book_case")) {
                    GetPageAttribute.getInstance().rate = GetBookCase.getInstance().mList.get(GetPageAttribute.getInstance().bookId).rate;
                    GetBookCase.getInstance().mList.get(GetPageAttribute.getInstance().bookId).rate = 0;
                }
                if(GetPageAttribute.getInstance().source.equals("book_intro")){
                    GetPageAttribute.getInstance().rate= 0;
                }

                getSharedPreferences("setting_pref",MODE_PRIVATE).edit().putBoolean("isChanged",true).commit();
                GetPageAttribute.getInstance().isChanged=true;

                onBackPressed();


            }
        });



    }


}
