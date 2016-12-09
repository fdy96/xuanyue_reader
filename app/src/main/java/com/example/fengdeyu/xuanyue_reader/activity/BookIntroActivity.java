package com.example.fengdeyu.xuanyue_reader.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.fengdeyu.xuanyue_reader.R;

public class BookIntroActivity extends AppCompatActivity {

    private ImageView iv_back;
    private Button btn_start_read;

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
    }
}
