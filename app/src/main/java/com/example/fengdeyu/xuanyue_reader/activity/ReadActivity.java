package com.example.fengdeyu.xuanyue_reader.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.fragment.ReadPageFragment;
import com.example.fengdeyu.xuanyue_reader.fragment.ReadScrollFragment;
import com.example.fengdeyu.xuanyue_reader.other.GetBookCase;
import com.example.fengdeyu.xuanyue_reader.other.GetChapterContent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadActivity extends AppCompatActivity {
    private ImageView iv_back;

    int bookId;


    private LinearLayout ll_book_read_top;
    private LinearLayout ll_book_read_bottom;


    private TextView tv_chapter_content;
    private TextView tv_chapter_download;

    private Button btn_paging;          ///////


    Fragment mRead;

    public String mContents="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);



        btn_paging= (Button)findViewById(R.id.paging);         //////

        btn_paging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(ReadActivity.this, ReadPageActivity.class).putExtra("contents",tv_contents.getText().toString()));


                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                mRead = new ReadScrollFragment();
                transaction.replace(R.id.id_content, mRead);
                transaction.commit();

            }
        });




        bookId = getIntent().getIntExtra("bookId", 0);


        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });






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

        tv_chapter_download= (TextView) findViewById(R.id.tv_chapter_download);
        tv_chapter_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ChapterDownloadActivity.class).putExtra("bookId",bookId));
            }
        });




    }

    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mRead = new ReadPageFragment();
        transaction.replace(R.id.id_content, mRead);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String url;


        if(getIntent().getStringExtra("intoWay").equals("bookCase")) {
            if(GetBookCase.getInstance().mList.get(bookId).mChapterList.get(GetChapterContent.getInstance().currentChapter).isDownload){
                url=GetBookCase.getInstance().mList.get(bookId).mChapterList.get(GetChapterContent.getInstance().currentChapter).chapter_local_url;
            }else {
                url=GetBookCase.getInstance().mList.get(bookId).mChapterList.get(GetChapterContent.getInstance().currentChapter).chapter_url;
            }

        }else{
            url=GetChapterContent.getInstance().mList.get(GetChapterContent.getInstance().currentChapter).chapter_url;
        }
        new loadContentsAsyncTask().execute(url);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(getIntent().getStringExtra("intoWay").equals("bookCase")) {

            GetBookCase.getInstance().mList.get(bookId).currentChapter = GetChapterContent.getInstance().currentChapter;
        }
        GetChapterContent.getInstance().clear();
    }






    public void toggleReadBar(){
        if(ll_book_read_top.getVisibility()==View.GONE){
            ll_book_read_top.setVisibility(View.VISIBLE);
            ll_book_read_bottom.setVisibility(View.VISIBLE);
        }else {
            ll_book_read_top.setVisibility(View.GONE);
            ll_book_read_bottom.setVisibility(View.GONE);
        }
    }

    private String read(String url) {
        try {
            FileInputStream inputStream=new FileInputStream(url);
            byte[] bytes = new byte[1024];
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            while (inputStream.read(bytes) != -1) {
                arrayOutputStream.write(bytes, 0, bytes.length);
            }
            inputStream.close();
            arrayOutputStream.close();
            String contents = new String(arrayOutputStream.toByteArray());

            return contents;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public class loadContentsAsyncTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            if(getIntent().getStringExtra("intoWay").equals("bookCase")&&
                    GetBookCase.getInstance().mList.get(bookId).mChapterList.get(GetChapterContent.getInstance().currentChapter).isDownload){
                return read(params[0]);
            }

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

            if(contents!=null) {
                 StringBuilder sb = new StringBuilder(contents);
                int i = 0;
                while (sb.indexOf(" ", i + 3) != -1) {
                    i = sb.indexOf(" ", i + 3);
                    sb.insert(i, "\n");
                }


                mContents=sb.toString();


                setDefaultFragment();


            }

        }
    }



}
