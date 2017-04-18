package com.example.fengdeyu.xuanyue_reader.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fengdeyu.xuanyue_reader.R;

public class FindCategoryActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button_1;
    private Button button_2;
    private Button button_3;
    private Button button_4;
    private Button button_5;
    private Button button_6;
    private Button button_7;
    private Button button_8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_category);

        button_1= (Button) findViewById(R.id.category_xuanhuan);
        button_2= (Button) findViewById(R.id.category_qihuan);
        button_3= (Button) findViewById(R.id.category_wuxia);
        button_4= (Button) findViewById(R.id.category_xianxia);
        button_5= (Button) findViewById(R.id.category_dushi);
        button_6= (Button) findViewById(R.id.category_lishi);
        button_7= (Button) findViewById(R.id.category_junshi);
        button_8= (Button) findViewById(R.id.category_kehuan);

        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_6.setOnClickListener(this);
        button_7.setOnClickListener(this);
        button_8.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        String URL="";
        String category="";


        switch (view.getId()){
            case R.id.category_xuanhuan:
                category="玄幻";
                URL="http://a.qidian.com/?size=-1&sign=-1&tag=-1&chanId=21&subCateId=-1&orderId=&update=-1&page=1&month=-1&style=1&action=-1&vip=-1";
                break;
            case R.id.category_qihuan:
                category="奇幻";
                URL="http://a.qidian.com/?size=-1&sign=-1&tag=-1&chanId=1&subCateId=-1&orderId=&update=-1&page=1&month=-1&style=1&action=-1&vip=-1";
                break;
            case R.id.category_wuxia:
                category="武侠";
                URL="http://a.qidian.com/?size=-1&sign=-1&tag=-1&chanId=2&subCateId=-1&orderId=&update=-1&page=1&month=-1&style=1&action=-1&vip=-1";
                break;
            case R.id.category_xianxia:
                category="仙侠";
                URL="http://a.qidian.com/?size=-1&sign=-1&tag=-1&chanId=22&subCateId=-1&orderId=&update=-1&page=1&month=-1&style=1&action=-1&vip=-1";
                break;
            case R.id.category_dushi:
                category="都市";
                URL="http://a.qidian.com/?size=-1&sign=-1&tag=-1&chanId=4&subCateId=-1&orderId=&update=-1&page=1&month=-1&style=1&action=-1&vip=-1";
                break;
            case R.id.category_lishi:
                category="历史";
                URL="http://a.qidian.com/?size=-1&sign=-1&tag=-1&chanId=5&subCateId=-1&orderId=&update=-1&page=1&month=-1&style=1&action=-1&vip=-1";
                break;
            case R.id.category_junshi:
                category="军事";
                URL="http://a.qidian.com/?size=-1&sign=-1&tag=-1&chanId=6&subCateId=-1&orderId=&update=-1&page=1&month=-1&style=1&action=-1&vip=-1";
                break;
            case R.id.category_kehuan:
                category="科幻";
                URL="http://a.qidian.com/?size=-1&sign=-1&tag=-1&chanId=9&subCateId=-1&orderId=&update=-1&page=1&month=-1&style=1&action=-1&vip=-1";
                break;
        }

        startActivity(new Intent(FindCategoryActivity.this,FindCategoryResultActivity.class).putExtra("category",category).putExtra("URL",URL));



    }

    public void onBack(View v){
        onBackPressed();
    }
}
