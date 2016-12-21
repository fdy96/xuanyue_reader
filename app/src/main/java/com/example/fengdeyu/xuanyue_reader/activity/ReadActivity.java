package com.example.fengdeyu.xuanyue_reader.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.other.GetBookCase;
import com.example.fengdeyu.xuanyue_reader.other.GetChapterContent;
import com.example.fengdeyu.xuanyue_reader.other.MyScrollView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import static android.R.attr.cacheColorHint;
import static android.R.attr.statusBarColor;

public class ReadActivity extends AppCompatActivity {
    private ImageView iv_back;

    int bookId;


    private LinearLayout ll_book_read_top;
    private LinearLayout ll_book_read_bottom;

    private TextView tv_contents;

    private TextView tv_chapter_content;

    private MyScrollView scrollView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        bookId = getIntent().getIntExtra("bookId", 0);


        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        scrollView= (MyScrollView) findViewById(R.id.my_scroll_view);

        tv_contents= (TextView) findViewById(R.id.tv_contents);




        ll_book_read_top= (LinearLayout) findViewById(R.id.ll_book_read_top);
        ll_book_read_bottom= (LinearLayout) findViewById(R.id.ll_book_read_bottom);

        tv_chapter_content= (TextView) findViewById(R.id.tv_chapter_content);

        tv_chapter_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ChapterContentActivity.class).putExtra("bookId",bookId).putExtra("intoWay",getIntent().getStringExtra("intoWay"));
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        String url;

        if(getIntent().getStringExtra("intoWay").equals("bookCase")) {
            url=GetBookCase.getInstance().mList.get(bookId).mChapterList.get(GetChapterContent.getInstance().currentChapter).chapter_url;

        }else{
            url=GetChapterContent.getInstance().mList.get(GetChapterContent.getInstance().currentChapter).chapter_url;
        }
        new loadContentsAsyncTask().execute(url);

        scrollView.scrollTo(0,0);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(getIntent().getStringExtra("intoWay").equals("bookCase")) {

            GetBookCase.getInstance().mList.get(bookId).currentChapter = GetChapterContent.getInstance().currentChapter;
        }
        GetChapterContent.getInstance().clear();
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Display display=getWindowManager().getDefaultDisplay();

        float x=event.getX();
        float y=event.getY();

        int touchEvent = event.getAction();

        switch (touchEvent){
            case MotionEvent.ACTION_DOWN:
                Toast.makeText(ReadActivity.this,"Down",Toast.LENGTH_SHORT).show();

            case MotionEvent.ACTION_UP:
                if(x>display.getWidth()/4&&x<3*display.getWidth()/4&&y>display.getHeight()/4&&y<3*display.getHeight()/4){
                    toggleReadBar();

                }

                break;


        }
        return super.onTouchEvent(event);
    }



    private void toggleReadBar(){
        if(ll_book_read_top.getVisibility()==View.GONE){
            ll_book_read_top.setVisibility(View.VISIBLE);
            ll_book_read_bottom.setVisibility(View.VISIBLE);
        }else {
            ll_book_read_top.setVisibility(View.GONE);
            ll_book_read_bottom.setVisibility(View.GONE);
        }
    }

    public class loadContentsAsyncTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                Document doc= Jsoup.connect(params[0]).get();
                String contents=doc.getElementById("contents").text();
                return contents;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String contents) {
            super.onPostExecute(contents);

            StringBuilder sb =new StringBuilder(contents);
            int i=0;



            while (sb.indexOf(" ",i+3)!=-1){
                i=sb.indexOf(" ",i+3);
                sb.insert(i,"\n");
            }

            tv_contents.setText(sb);

        }
    }


}
