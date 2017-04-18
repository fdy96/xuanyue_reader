package com.example.fengdeyu.xuanyue_reader.other;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.activity.FindCategoryResultActivity;
import com.example.fengdeyu.xuanyue_reader.bean.BookItemBean;

import java.util.ArrayList;
import java.util.List;

public class ConnectTestActivity extends AppCompatActivity {

    private String URL = "http://a.qidian.com/?size=-1&sign=-1&tag=-1&chanId=21&subCateId=-1&orderId=&update=-1&page=1&month=-1&style=1&action=-1&vip=-1";
    private List<BookItemBean> mList = new ArrayList<>();

    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_test);


        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(ConnectTestActivity.this, FindCategoryResultActivity.class));

            }
        });

    }
}

