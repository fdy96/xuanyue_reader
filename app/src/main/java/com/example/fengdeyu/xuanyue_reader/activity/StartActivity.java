package com.example.fengdeyu.xuanyue_reader.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.other.ConnectTestActivity;
import com.example.fengdeyu.xuanyue_reader.other.GetPageAttribute;

/**
 * 开始欢迎界面，每次打开app时显示
 */
public class StartActivity extends Activity {
    private TextView tv_start;      //直接跳转文本按钮
    private boolean isOpen=false;   //是否点击跳转标志

    private boolean isFirstStart;   //是否第一次打开标志
    SharedPreferences setting;      //存取app设置的文件

    public int battery;
    //注册广播接受者java代码
    IntentFilter filter;
    //创建广播接受者对象
    BatteryReceiver receiver;

    /**
     * 广播接受者
     */
    class BatteryReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            //判断它是否是为电量变化的Broadcast Action
            if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){
                //获取当前电量
                int level = intent.getIntExtra("level", 0);
                //电量的总刻度
                int scale = intent.getIntExtra("scale", 100);
                //把它转成百分比
                //tv.setText("电池电量为"+((level*100)/scale)+"%");
                GetPageAttribute.getInstance().battery=(level*100)/scale;

                Log.i("info","level:"+level+"scale:"+scale+"battery:"+battery);

            }
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        receiver=new BatteryReceiver();

        registerReceiver(receiver,filter);
        GetPageAttribute.getInstance().battery=battery;

        setting=getSharedPreferences("setting_pref",MODE_PRIVATE);    //获取设置文件中的是否第一次打开标志
        isFirstStart=setting.getBoolean("FIRST",true);              //默认第一次打开


        tv_start= (TextView) findViewById(R.id.tv_start);
        tv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {         //文本按钮监听事件
                isOpen=true;                        //点击跳转为true
                toStart();
            }
        });

        handler.sendEmptyMessageDelayed(0,3000);    //等待3秒开始跳转

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(!isOpen){                            //如果没有点击跳转的话
                toStart();
            }
            super.handleMessage(msg);
        }
    };

    public void toStart(){                          //开始跳转

        if(isFirstStart){                           //如果是第一次打开
            setting.edit().putBoolean("FIRST",false).commit();                      //将标志设为false
            startActivity(new Intent(StartActivity.this,GuideViewActivity.class));      //跳转到引导页
            finish();                               //销毁当前页

        }else{                                      //如果不是第一次打开
            startActivity(new Intent(StartActivity.this, MainActivity.class));          //跳转到主界面
            finish();                               //销毁当前页
        }
    }


}
