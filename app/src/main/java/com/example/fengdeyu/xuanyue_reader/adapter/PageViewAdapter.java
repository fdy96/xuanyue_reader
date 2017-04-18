package com.example.fengdeyu.xuanyue_reader.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.BatteryManager;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.activity.ReadActivity;
import com.example.fengdeyu.xuanyue_reader.fragment.ReadPageFragment;
import com.example.fengdeyu.xuanyue_reader.other.GetBookCase;
import com.example.fengdeyu.xuanyue_reader.other.GetChapterContent;
import com.example.fengdeyu.xuanyue_reader.other.GetPageAttribute;
import com.example.fengdeyu.xuanyue_reader.other.MyReadInterface;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by fengdeyu on 2017/1/7.
 */

public class PageViewAdapter extends PageAdapter {

    Context mContext;
    ArrayList<String> mList;
    AssetManager am;
    Calendar mCalendar=Calendar.getInstance();


    private ReadPageFragment fragment;

    public int battery;
    //注册广播接受者java代码
    IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    //创建广播接受者对象
    BatteryReceiver receiver=new BatteryReceiver();


    public PageViewAdapter(Context mContext, ArrayList<String> mList, ReadPageFragment fragment){
        this.mContext=mContext;
        this.mList=mList;
        am=mContext.getAssets();

        this.fragment=fragment;

        mContext.registerReceiver(receiver,filter);


    }




    @Override
    public View getView() {
        View view= LayoutInflater.from(mContext).inflate(R.layout.page_layout,null);
        return view;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public int getCurrentPage() {
        int currentPage;
        currentPage=(int)(GetPageAttribute.getInstance().rate*getCount());
        if(currentPage==0){
            return 1;
        }
        else {
            return currentPage;
        }



    }

    @Override
    public void dataUpdate(int i) {
        fragment.dataUpdate(i);
    }

    @Override
    public void addContent(View view, int position) {
        if (position-1<0){
            Log.i("info","已经是第一页1");
            return;
        }
        if (position-1>=getCount()){
            Log.i("info","已经是最后一页1");
            return;
        }


        TextView tv_contents = (TextView) view.findViewById(R.id.tv_contents);
        TextView tv_page_num= (TextView) view.findViewById(R.id.tv_page_num);
        TextView tv_chapter_name= (TextView) view.findViewById(R.id.tv_chapter_name);
        TextView tv_current_time= (TextView) view.findViewById(R.id.tv_current_time);
        TextView tv_current_battery= (TextView) view.findViewById(R.id.tv_current_battery);
        RelativeLayout rl_page_layout= (RelativeLayout) view.findViewById(R.id.rl_page_layout);


        tv_contents.setText(mList.get(position - 1));
        tv_contents.setTextSize(GetPageAttribute.getInstance().textSize);
        Typeface tf=Typeface.createFromAsset(mContext.getAssets(),GetPageAttribute.getInstance().font_typeface);
        tv_contents.setTypeface(tf);

        mContext.registerReceiver(receiver,filter);





        tv_page_num.setText("第"+position+"/"+getCount()+"页");
        tv_chapter_name.setText(GetPageAttribute.getInstance().chapterName);
        tv_current_time.setText(getCurrentTime());
        tv_current_battery.setText(GetPageAttribute.getInstance().battery+"");


        rl_page_layout.setBackgroundColor(GetPageAttribute.getInstance().bg_theme);

        tv_contents.setTextColor(GetPageAttribute.getInstance().textColor);
        tv_page_num.setTextColor(GetPageAttribute.getInstance().textColor);
        tv_current_time.setTextColor(GetPageAttribute.getInstance().textColor);
        tv_current_battery.setTextColor(GetPageAttribute.getInstance().textColor);


    }

    public String getCurrentTime(){
        long time=System.currentTimeMillis();
        mCalendar.setTimeInMillis(time);
        if(mCalendar.get(Calendar.MINUTE)<10){
            return mCalendar.get(Calendar.HOUR)+":0"+mCalendar.get(Calendar.MINUTE);
        }else {
            return mCalendar.get(Calendar.HOUR)+":"+mCalendar.get(Calendar.MINUTE);
        }
    }











    /**
     * 广播接受者
     */
    class BatteryReceiver extends BroadcastReceiver{

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
                GetPageAttribute.getInstance().battery=(level*100)/scale;


            }
        }

    }
}
