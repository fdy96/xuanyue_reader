package com.example.fengdeyu.xuanyue_reader.other;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.bean.BookItemBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConnectTestActivity extends AppCompatActivity {

    private String URL="http://zhannei.baidu.com/cse/search?s=17233375349940438896&entry=1&q=仙逆";
    private List<BookItemBean> mList=new ArrayList<>();

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_test);


        button= (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ConnectAsyncTask().execute(URL);
            }
        });

    }

    class ConnectAsyncTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {



            try {

                Document doc= Jsoup.connect(params[0]).get();
                Elements links=doc.select(".result-item");

                for (Element link:links){
                    BookItemBean bookItemBean=new BookItemBean();

                    Elements hrefs=link.select("div[onclick]");
                    for(Element href:hrefs){
                        String s1=href.attr("onclick");
                        String s2=s1.substring(s1.indexOf("'")+1,s1.lastIndexOf("'"));


                        bookItemBean.bookHref=s2;//获取小说章节目录链接
                    }

                    Elements srcs=link.select("img[src]");
                    for(Element src:srcs){
                        String s1=src.attr("src");
                        bookItemBean.bookIconUrl=s1;//获取小说图片链接
                    }

                    Elements titles=link.select(".result-item-title.result-game-item-title");
                    for(Element title:titles){
                        String s1=title.text();
                        bookItemBean.bookIconUrl=s1;//获取小说名称
                    }

                    Elements authors=link.select(".result-game-item-info-tag");
                    String s=authors.first().text().trim();
                    bookItemBean.bookAuthor=s;//获取小说作者
                    Log.i("info",s);


                    Elements contents=link.select(".result-game-item-uspan");

                    for(Element content:contents){
                        String s1=content.text();
                        bookItemBean.bookAuthor="最新章节:"+s1;//获取小说最新章节
                    }

                    mList.add(bookItemBean);



                }


            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }




}
