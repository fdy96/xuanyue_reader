package com.example.fengdeyu.xuanyue_reader.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.adapter.ChapterContentAdapter;
import com.example.fengdeyu.xuanyue_reader.bean.ChapterContentBean;
import com.example.fengdeyu.xuanyue_reader.other.GetChapterContent;

import java.util.ArrayList;
import java.util.List;

public class ChapterContentActivity extends Activity {
    private RecyclerView chapter_content_recycler_view;
    private String URL="http://www.23us.so/files/article/html/10/10674/index.html";
    private int currentChapter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_content);


        chapter_content_recycler_view= (RecyclerView) findViewById(R.id.chapter_content_recycler_view);


        new GetChapterContentAsyncTask().execute(URL);

    }

    class GetChapterContentAsyncTask extends AsyncTask<String,Void,List<ChapterContentBean>>{

        @Override
        protected List<ChapterContentBean> doInBackground(String... params) {
            List<ChapterContentBean> mList=new ArrayList<>();
            if(GetChapterContent.getInstance().mList.size()==0) {
                GetChapterContent.getInstance().loadChapterContent(params[0]);
            }
            mList=GetChapterContent.getInstance().mList;
            return mList;
        }

        @Override
        protected void onPostExecute(final List<ChapterContentBean> mList) {
            super.onPostExecute(mList);
            ChapterContentAdapter adapter=new ChapterContentAdapter(ChapterContentActivity.this,mList, currentChapter);
            chapter_content_recycler_view.setLayoutManager(new LinearLayoutManager(chapter_content_recycler_view.getContext()));
            chapter_content_recycler_view.setAdapter(adapter);
            chapter_content_recycler_view.scrollToPosition(currentChapter);


            adapter.setOnItemClickListener(new ChapterContentAdapter.onItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Log.i("info",mList.get(position).chapter_name);

                }
            });

        }
    }
}
