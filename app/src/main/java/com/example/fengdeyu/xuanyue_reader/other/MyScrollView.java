package com.example.fengdeyu.xuanyue_reader.other;

import android.content.Context;
import android.util.AttributeSet;
import android.util.EventLog;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by fengdeyu on 2016/12/14.
 */

public class MyScrollView extends ScrollView {
    private boolean flag=false;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        super.onTouchEvent(ev);
        if(ev.getAction()==MotionEvent.ACTION_DOWN){
            return true;
        }
        if(ev.getAction()==MotionEvent.ACTION_MOVE){
            flag=true;
            return true;
        }
        if(flag){
            if(ev.getAction()==MotionEvent.ACTION_UP){
                flag=false;
                return true;
            }
        }


        return false;




    }


}
