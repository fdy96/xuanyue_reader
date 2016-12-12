package com.example.fengdeyu.xuanyue_reader.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.other.ImageLoader;

public class BookIntroActivity extends AppCompatActivity {

    private ImageView iv_back;
    private Button btn_start_read;

    private TextView bookTitle;
    private TextView bookAuthor;
    private TextView bookContent;
    private TextView bookIntro;
    private ImageView bookIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_intro);


        iv_back= (ImageView) findViewById(R.id.iv_back);
        btn_start_read= (Button) findViewById(R.id.btn_start_read);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_start_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookIntroActivity.this,ReadActivity.class));
            }
        });


        init();

        Intent intent=getIntent();
        bookTitle.setText(intent.getStringExtra("bookTitle"));
        bookAuthor.setText(intent.getStringExtra("bookAuthor"));
        bookContent.setText(intent.getStringExtra("bookContent"));
        bookIntro.setText(intent.getStringExtra("bookIntro"));
        bookIcon.setTag(intent.getStringExtra("bookIconUrl"));
        new ImageLoader().showImageByThread(bookIcon,intent.getStringExtra("bookIconUrl"));




    }

    public void init(){
        bookTitle= (TextView) findViewById(R.id.tv_book_title);
        bookAuthor= (TextView) findViewById(R.id.tv_book_author);
        bookContent= (TextView) findViewById(R.id.tv_book_content);
        bookIntro= (TextView) findViewById(R.id.tv_book_intro);
        bookIcon= (ImageView) findViewById(R.id.iv_book_icon);
    }

}
