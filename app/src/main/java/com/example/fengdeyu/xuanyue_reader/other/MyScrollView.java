package com.example.fengdeyu.xuanyue_reader.other;

import android.content.Context;
import android.util.AttributeSet;
import android.util.EventLog;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.fengdeyu.xuanyue_reader.activity.ReadActivity;

/**
 * Created by fengdeyu on 2016/12/14.
 */

public class MyScrollView extends ScrollView {
    private boolean flag=false;

    public boolean isClickCenter=false;



    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
    }


    float down_X=0;
    float down_Y=0;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        super.onTouchEvent(ev);



        if(ev.getAction()==MotionEvent.ACTION_DOWN){
            down_X=ev.getX();
            down_Y=ev.getY();
        }

        if(ev.getAction()==MotionEvent.ACTION_UP){

            if(-5<(ev.getX()-down_X)&&(ev.getX()-down_X)<5&&-5<(ev.getY()-down_Y)&&(ev.getY()-down_Y)<5) {

                if(down_X>getWidth()/4&&down_X<3*getWidth()/4&&down_Y>getHeight()/4&&down_Y<3*getHeight()/4){

                    isClickCenter=true;

                }
            }
        }

        return true;

    }


}
