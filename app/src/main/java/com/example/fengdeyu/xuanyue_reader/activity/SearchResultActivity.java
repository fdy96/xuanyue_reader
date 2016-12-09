package com.example.fengdeyu.xuanyue_reader.activity;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    private EditText et_search;
//
//    private ImageView iv_back;
    private ImageView iv_search;

    private RecyclerView bookItemView;
    private List<BookItemBean> mList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

//
//        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_search= (ImageView) findViewById(R.id.iv_search);


        et_search= (EditText) findViewById(R.id.et_search);
        et_search.setText(getIntent().getStringExtra("keywords"));


        bookItemView= (RecyclerView) findViewById(R.id.bookitem_recycler_view);
        loadList();

        BookItemAdapter bookItemAdapter=new BookItemAdapter(SearchResultActivity.this,mList);


        bookItemView.setLayoutManager(new LinearLayoutManager(bookItemView.getContext()));
        bookItemView.setAdapter(bookItemAdapter);

        bookItemAdapter.setOnItemClickListener(new BookItemAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(SearchResultActivity.this, BookIntroActivity.class));
            }
        });



//        iv_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//
//        iv_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //search(et_search.getText().toString());
//            }
//        });

    }


    public void loadList(){//加载书架数据
        BookItemBean bookItemBean1=new BookItemBean();
        bookItemBean1.bookIconUrl=R.mipmap.book_icon2;
        bookItemBean1.bookTitle="仙逆";
        bookItemBean1.bookAuthor="作者:耳根";
        bookItemBean1.bookcontent="第2088章 蓦然回首（结局）";

        mList.add(bookItemBean1);

        BookItemBean bookItemBean2=new BookItemBean();
        bookItemBean2.bookIconUrl=R.mipmap.book_icon;
        bookItemBean2.bookTitle="太浩";
        bookItemBean2.bookAuthor="作者:无极书虫";
        bookItemBean2.bookcontent="同人:没有梦蝶的世界";

        mList.add(bookItemBean2);

        BookItemBean bookItemBean3=new BookItemBean();
        bookItemBean3.bookIconUrl=R.mipmap.book_icon1;
        bookItemBean3.bookTitle="岁月是朵两生花";
        bookItemBean3.bookAuthor="作者:唐七公子";
        bookItemBean3.bookcontent="第二十六章 两生花";

        mList.add(bookItemBean3);
    }


    private void search(String keywords){
        //Intent intent=new Intent(SearchResultActivity.this,SearchResultActivity.class);
        //intent.putExtra("keywords",keywords);
        //startActivity(intent);
    }
}
