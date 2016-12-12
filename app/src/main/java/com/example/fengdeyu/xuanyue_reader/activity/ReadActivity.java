package com.example.fengdeyu.xuanyue_reader.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.fengdeyu.xuanyue_reader.R;

import static android.R.attr.statusBarColor;

public class ReadActivity extends AppCompatActivity {
    private ImageView iv_back;


    private LinearLayout ll_book_read_top;
    private LinearLayout ll_book_read_bottom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);




        iv_back= (ImageView) findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ll_book_read_top= (LinearLayout) findViewById(R.id.ll_book_read_top);
        ll_book_read_bottom= (LinearLayout) findViewById(R.id.ll_book_read_bottom);



    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Display display=getWindowManager().getDefaultDisplay();

        float x=event.getX();
        float y=event.getY();

        int touchEvent = event.getAction();

        switch (touchEvent){
            case MotionEvent.ACTION_DOWN:
                if(x>display.getWidth()/4&&x<3*display.getWidth()/4&&y>display.getHeight()/4&&y<3*display.getHeight()/4){
                    toggleReadBar();

                }
                break;

        }
        return super.onTouchEvent(event);
    }


//显示状态栏

//    private void toggleReadBar() {
//        WindowManager.LayoutParams attrs = getWindow().getAttributes();
//        if(ll_book_read_top.getVisibility()==View.GONE){
//            attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
//            getWindow().setAttributes(attrs);
//            handler1.sendEmptyMessageDelayed(0,200);
//
//
//        }else {
//
//            ll_book_read_top.setVisibility(View.GONE);
//            ll_book_read_bottom.setVisibility(View.GONE);
//            handler2.sendMessage(new Message());
//        }
//
//    }
//    private Handler handler1 = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            ll_book_read_top.setVisibility(View.VISIBLE);
//            ll_book_read_bottom.setVisibility(View.VISIBLE);
//            super.handleMessage(msg);
//        }
//    };
//    private Handler handler2 = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            WindowManager.LayoutParams attrs = getWindow().getAttributes();
//            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
//            getWindow().setAttributes(attrs);
//            super.handleMessage(msg);
//        }
//    };
//
// 不显示
    private void toggleReadBar(){
        if(ll_book_read_top.getVisibility()==View.GONE){
            ll_book_read_top.setVisibility(View.VISIBLE);
            ll_book_read_bottom.setVisibility(View.VISIBLE);
        }else {
            ll_book_read_top.setVisibility(View.GONE);
            ll_book_read_bottom.setVisibility(View.GONE);
        }
    }
}
