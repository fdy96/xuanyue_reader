package com.example.fengdeyu.xuanyue_reader.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by fengdeyu on 2017/3/21.
 */

public abstract class MyDialog extends Dialog {

    public int pic_num=0;

    public MyDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public EditText getEditText(int id){return (EditText)findViewById(id);}

    public TextView getTextView(int id){return (TextView)findViewById(id);}

    public DatePicker getDatePicker(int id){return (DatePicker)findViewById(id);}

    public void setPic_num(int i){
        pic_num=i;
    }
    public int getPic_num(){
        return pic_num;
    }
}
