package com.example.fengdeyu.xuanyue_reader.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.other.ConnectTestActivity;

public class StartActivity extends Activity {

    private boolean isFirstStart;
    SharedPreferences setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        setting=getSharedPreferences("first_pref",MODE_PRIVATE);
        isFirstStart=setting.getBoolean("FIRST",true);

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
