package com.example.fengdeyu.xuanyue_reader.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by fengdeyu on 2017/1/16.
 */

public class MyTextView extends TextView {

    public boolean isClickCenter=false;
    float down_X=0;
    float down_Y=0;

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        if(ev.getAction()==MotionEvent.ACTION_DOWN){
            down_X=ev.getX();
            down_Y=ev.getY();
        }

        if(ev.getAction()==MotionEvent.ACTION_UP){
            Log.i("info","up");
            if(-5<(ev.getX()-down_X)&&(ev.getX()-down_X)<5&&-5<(ev.getY()-down_Y)&&(ev.getY()-down_Y)<5) {
                Log.i("info","static");

                Log.i("info","down_X="+down_X+",getWidth="+getWidth());
                Log.i("info","down_Y="+down_Y+",getHeight="+getMeasuredHeight());
                Log.i("info","1:"+getLineHeight());
                if(down_X>getWidth()/4&&down_X<3*getWidth()/4&&down_Y>getLineHeight()*6&&down_Y<getLineHeight()*18){
                    Log.i("info","true");
                    isClickCenter=true;

                }
            }
        }

        return true;
    }
}
