package com.example.fengdeyu.xuanyue_reader.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.fengdeyu.xuanyue_reader.R;

public class FindRanklistActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_ranklist_1;
    private TextView tv_ranklist_2;
    private TextView tv_ranklist_3;
    private TextView tv_ranklist_4;
    private TextView tv_ranklist_5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranklist);

        tv_ranklist_1= (TextView) findViewById(R.id.tv_ranklist_1);
        tv_ranklist_2= (TextView) findViewById(R.id.tv_ranklist_2);
        tv_ranklist_3= (TextView) findViewById(R.id.tv_ranklist_3);
        tv_ranklist_4= (TextView) findViewById(R.id.tv_ranklist_4);
        tv_ranklist_5= (TextView) findViewById(R.id.tv_ranklist_5);

        tv_ranklist_1.setOnClickListener(this);
        tv_ranklist_2.setOnClickListener(this);
        tv_ranklist_3.setOnClickListener(this);
        tv_ranklist_4.setOnClickListener(this);
        tv_ranklist_5.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String URL="";
        String category="";
        switch (view.getId()){
            case R.id.tv_ranklist_1:
                URL="http://r.qidian.com/yuepiao?style=1";
                category="原创风云榜";
                break;
            case R.id.tv_ranklist_2:
                URL="http://r.qidian.com/hotsales?style=1";
                category="24小时热销榜";
                break;
            case R.id.tv_ranklist_3:
                URL="http://r.qidian.com/collect?style=1";
                category="收藏榜";
                break;
            case R.id.tv_ranklist_4:
                URL="http://r.qidian.com/recom?style=1";
                category="推荐票榜";
                break;
            case R.id.tv_ranklist_5:
                URL="http://r.qidian.com/fin?style=1";
                category="完本榜";
                break;
        }
        startActivity(new Intent(FindRanklistActivity.this,FindCategoryResultActivity.class).putExtra("category",category).putExtra("URL",URL));


    }

    public void onBack(View v){
        onBackPressed();
    }
}
