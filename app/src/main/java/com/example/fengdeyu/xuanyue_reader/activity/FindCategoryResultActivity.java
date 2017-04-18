package com.example.fengdeyu.xuanyue_reader.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.adapter.BookItemAdapter;
import com.example.fengdeyu.xuanyue_reader.bean.BookItemBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FindCategoryResultActivity extends AppCompatActivity {

    private RecyclerView bookItemView;
    private TextView tv_theme;

    private String URL = "http://a.qidian.com/?size=-1&sign=-1&tag=-1&chanId=21&subCateId=-1&orderId=&update=-1&page=1&month=-1&style=1&action=-1&vip=-1";
    private List<BookItemBean> mList = new ArrayList<>();

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            BookItemAdapter adapter=new BookItemAdapter(FindCategoryResultActivity.this,mList);
            bookItemView.setLayoutManager(new LinearLayoutManager(bookItemView.getContext()));
            bookItemView.setAdapter(adapter);

            adapter.setOnItemClickListener(new BookItemAdapter.onItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    startActivity(new Intent(FindCategoryResultActivity.this,SearchResultActivity.class).putExtra("keywords",mList.get(position).bookTitle));
                }
            });

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_category_result);

        tv_theme= (TextView) findViewById(R.id.tv_theme);
        tv_theme.setText(getIntent().getStringExtra("category"));

        URL=getIntent().getStringExtra("URL");

        bookItemView= (RecyclerView) findViewById(R.id.bookitem_recycler_view);

        new Thread(){
            @Override
            public void run() {
                super.run();

                Document doc= null;
                try {
                    doc = Jsoup.connect(URL).get();
                    Elements links=doc.select("li[data-rid]");
                    for (Element link:links){
                        BookItemBean bookItemBean=new BookItemBean();

                        /**
                         * 小说名称和图片
                         */
                        Elements imgs=link.select("h4");
                        for (Element img:imgs){
                            bookItemBean.bookTitle=img.select("a").text();
                            bookItemBean.bookIconUrl="http://qidian.qpic.cn/qdbimg/349573/"+img.select("a").attr("data-bid")+"/180";
                            Log.i("小说名称：",img.select("a").text());
                            Log.i("图片链接：","http://qidian.qpic.cn/qdbimg/349573/"+img.select("a").attr("data-bid")+"/180");
                        }
                        /**
                         * 作者
                         */
                        Elements authors=link.select("p.author");
                        for (Element author:authors){
                            bookItemBean.bookAuthor=author.text();
                            Log.i("作品信息：",author.text());
                        }
                        /**
                         * 小说简介
                         */
                        Elements intros=link.select("p.intro");
                        for (Element intro:intros){
                            bookItemBean.bookContent=intro.text();
                            Log.i("小说简介：",intro.text());
                        }

                        mList.add(bookItemBean);
                    }


                } catch (IOException e1) {
                    e1.printStackTrace();
                }


                handler.sendEmptyMessage(0);
            }
        }.start();
    }

    public void onBack(View v){
        onBackPressed();
    }
}
