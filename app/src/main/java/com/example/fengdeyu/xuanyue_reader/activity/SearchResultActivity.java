package com.example.fengdeyu.xuanyue_reader.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.adapter.BookItemAdapter;
import com.example.fengdeyu.xuanyue_reader.bean.BookItemBean;
import com.example.fengdeyu.xuanyue_reader.sqlite.RecordSQLiteOpenHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    private String urlAddress="http://zhannei.baidu.com/cse/search?s=10048850760735184192&entry=1&ie=gbk&q=";
    private EditText et_search;

    private RecyclerView bookItemView;

    List<String> bookIntro=new ArrayList<>();

    private ImageView iv_search;
    private ImageView iv_back;

    private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        et_search= (EditText) findViewById(R.id.et_search);
        et_search.setText(getIntent().getStringExtra("keywords"));



        String URL=urlAddress+cnToUnicode(et_search.getText().toString());


        bookItemView= (RecyclerView) findViewById(R.id.bookitem_recycler_view);
        new ConnectAsyncTask().execute(URL);


        iv_search= (ImageView) findViewById(R.id.iv_search);
        iv_back= (ImageView) findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL=urlAddress+cnToUnicode(et_search.getText().toString());
                if(!hasData(et_search.getText().toString())) {
                    insertData(et_search.getText().toString());
                }
                bookIntro.clear();
                new ConnectAsyncTask().execute(URL);
            }
        });


    }


//    class ConnectAsyncTask extends AsyncTask<String,Void,List<BookItemBean>> {
//
//
//        @Override
//        protected List<BookItemBean> doInBackground(String... params) {
//            List<BookItemBean> mList=new ArrayList<>();
//
//
//
//
//            try {
//
//                Document doc= Jsoup.connect(params[0]).get();
//
//                Elements intros=doc.select(".result-game-item-desc");
//                for(Element intro:intros){
//                    String s1=intro.text();
//                    bookIntro.add(s1);
//                }
//
//
//
//                Elements links=doc.select(".result-item");
//
//
//                for (Element link:links){
//                    BookItemBean bookItemBean=new BookItemBean();
//
//
//                    Elements hrefs=link.select("div[onclick]");
//                    for(Element href:hrefs){
//                        String s1=href.attr("onclick");
//                        String s2=s1.substring(s1.indexOf("html"),s1.lastIndexOf("'"));
//                        String s3="http://www.23us.so/files/article/"+s2+"index.html";
//                        Log.i("info",s3);
//
//                        //Log.i("info","汪汪");
//
//
//                        bookItemBean.bookHref=s3;//获取小说章节目录链接
//                    }
//
//                    Elements srcs=link.select("img[src]");
//                    for(Element src:srcs){
//                        String s1=src.attr("src");
//                        bookItemBean.bookIconUrl=s1;//获取小说图片链接
//
//                    }
//
//                    Elements titles=link.select(".result-item-title.result-game-item-title");
//                    for(Element title:titles){
//                        String s1=title.text();
//                        bookItemBean.bookTitle=s1;//获取小说名称
//
//                    }
//
//                    Elements authors=link.select(".result-game-item-info-tag");
//                    String s=authors.first().text().trim();
//                    bookItemBean.bookAuthor=s;//获取小说作者
//
//
//
//                    Elements contents=link.select(".result-game-item-uspan");
//
//                    for(Element content:contents){
//                        String s1=content.text();
//                        bookItemBean.bookContent="最新章节:"+s1;//获取小说最新章节
//                    }
//
//
//
//
//                    mList.add(bookItemBean);
//
//
//
//                }
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//            return mList;
//        }



    class ConnectAsyncTask extends AsyncTask<String,Void,List<BookItemBean>> {


        @Override
        protected List<BookItemBean> doInBackground(String... params) {
            List<BookItemBean> mList=new ArrayList<>();




            try {

                Document doc= Jsoup.connect(params[0]).get();

                Elements intros=doc.select(".result-game-item-desc");
                for(Element intro:intros){
                    String s1=intro.text();
                    bookIntro.add(s1);
                }


                Elements links=doc.select("div.result-item");
                for (Element link:links){
                    BookItemBean bookItemBean=new BookItemBean();


                    Elements hrefs=link.select("div[onclick]");
                    for(Element href:hrefs){
                        String s1=href.attr("onclick");
                        String s2=s1.substring(s1.indexOf("'")+1,s1.lastIndexOf("'"));
                        String s3=s2.substring(s2.lastIndexOf("/")+1,s2.lastIndexOf("."));
                        String s4;
                        if(s3.length()<4){
                            s4="0";
                        }else {
                            s4 = s3.substring(0, s3.length() - 3);
                        }
                        String s5="http://www.biquge.com.tw/"+s4+"_"+s3+"/";


                        bookItemBean.bookHref=s5;//获取小说章节目录链接
                    }

                    Elements srcs=link.select("img[src]");
                    for(Element src:srcs){
                        String s1=src.attr("src");
                        bookItemBean.bookIconUrl=s1;//获取小说图片链接

                    }
//
                    Elements titles=link.select(".result-item-title.result-game-item-title");
                    for(Element title:titles){
                        String s1=title.text();
                        bookItemBean.bookTitle=s1;//获取小说名称

                    }

                    Elements infos=link.select(".result-game-item-info-tag");
                    String s=infos.first().text().trim();
                    bookItemBean.bookAuthor=s;//获取小说作者
                    bookItemBean.bookContent=infos.get(1).text().trim();//获取小说最新章节



                    mList.add(bookItemBean);



                }


            } catch (IOException e) {
                e.printStackTrace();
            }


            return mList;
        }



        @Override
        protected void onPostExecute(final List<BookItemBean> mList) {
            super.onPostExecute(mList);

            BookItemAdapter bookItemAdapter=new BookItemAdapter(SearchResultActivity.this,mList);
            bookItemView.setLayoutManager(new LinearLayoutManager(bookItemView.getContext()));
            bookItemView.setAdapter(bookItemAdapter);

            bookItemAdapter.setOnItemClickListener(new BookItemAdapter.onItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent =new Intent();
                    intent.setClass(SearchResultActivity.this, BookIntroActivity.class);
                    intent.putExtra("bookTitle",mList.get(position).bookTitle);
                    intent.putExtra("bookAuthor",mList.get(position).bookAuthor);
                    intent.putExtra("bookContent",mList.get(position).bookContent);
                    intent.putExtra("bookIconUrl",mList.get(position).bookIconUrl);
                    intent.putExtra("bookIntro",bookIntro.get(position));
                    intent.putExtra("bookHref",mList.get(position).bookHref);

                    startActivity(intent);
                }
            });

        }
    }

    public String cnToUnicode(String str){
        String result="";
        for (int i = 0; i < str.length(); i++){
            int chr1 = (char) str.charAt(i);
            if(chr1>=19968&&chr1<=171941){//汉字范围 \u4e00-\u9fa5 (中文)
                result+="\\u" + Integer.toHexString(chr1);
            }else{
                result+=str.charAt(i);
            }
        }
        return result;
    }

    /**
     * 插入数据
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }
    /**
     * 检查数据库中是否已经有该条记录
     */
    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }



}
