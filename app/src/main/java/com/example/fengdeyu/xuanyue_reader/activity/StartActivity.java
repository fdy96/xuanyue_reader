package com.example.fengdeyu.xuanyue_reader.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.other.ConnectTestActivity;
import com.example.fengdeyu.xuanyue_reader.other.DownloadTestActivity;
import com.example.fengdeyu.xuanyue_reader.other.SearchFileTestActivity;

public class StartActivity extends Activity {
    private TextView tv_start;
    private boolean isOpen=false;

    private boolean isFirstStart;
    SharedPreferences setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        setting=getSharedPreferences("first_pref",MODE_PRIVATE);
        isFirstStart=setting.getBoolean("FIRST",true);


        tv_start= (TextView) findViewById(R.id.tv_start);
        tv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpen=true;
                toLogin();
            }
        });

        handler.sendEmptyMessageDelayed(0,3000);    //等待3秒跳转到登录界面

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(!isOpen){
                toLogin();
            }
            super.handleMessage(msg);
        }
    };

    public void toLogin(){

        if(isFirstStart){
            setting.edit().putBoolean("FIRST",false).commit();
            startActivity(new Intent(StartActivity.this,GuideViewActivity.class));
            finish();

        }else{
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


    }


}
