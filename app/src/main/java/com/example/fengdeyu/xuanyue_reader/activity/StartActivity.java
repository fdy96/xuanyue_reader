package com.example.fengdeyu.xuanyue_reader.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fengdeyu.xuanyue_reader.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        handler.sendEmptyMessageDelayed(0,3000);    //等待3秒跳转到登录界面

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            toLogin();
            super.handleMessage(msg);
        }
    };

    public void toLogin(){
        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
