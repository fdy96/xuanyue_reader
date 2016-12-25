package com.example.fengdeyu.xuanyue_reader.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.adapter.ChapterContentAdapter;
import com.example.fengdeyu.xuanyue_reader.other.DownloadServer;
import com.example.fengdeyu.xuanyue_reader.other.GetBookCase;
import com.example.fengdeyu.xuanyue_reader.other.GetChapterContent;

public class ChapterDownloadActivity extends Activity {

    private TextView tv_download_type_1;
    private TextView tv_download_type_2;
    private TextView tv_download_type_3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_download);

        tv_download_type_1= (TextView) findViewById(R.id.tv_download_type_1);
        tv_download_type_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        new DownloadServer(getIntent().getIntExtra("bookId",0),GetChapterContent.getInstance().currentChapter,
                                GetChapterContent.getInstance().currentChapter+50,ChapterDownloadActivity.this).download();
                    }
                }.start();
                onBackPressed();

            }
        });
        tv_download_type_2= (TextView) findViewById(R.id.tv_download_type_2);
        tv_download_type_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        new DownloadServer(getIntent().getIntExtra("bookId",0),GetChapterContent.getInstance().currentChapter,
                                GetBookCase.getInstance().mList.get(getIntent().getIntExtra("bookId",0)).mChapterList.size(),ChapterDownloadActivity.this).download();
                    }
                }.start();
                onBackPressed();

            }
        });
        tv_download_type_3= (TextView) findViewById(R.id.tv_download_type_3);
        tv_download_type_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        new DownloadServer(getIntent().getIntExtra("bookId",0),0,
                                GetBookCase.getInstance().mList.get(getIntent().getIntExtra("bookId",0)).mChapterList.size(),ChapterDownloadActivity.this).download();
                    }
                }.start();
                onBackPressed();

            }
        });
    }


}
