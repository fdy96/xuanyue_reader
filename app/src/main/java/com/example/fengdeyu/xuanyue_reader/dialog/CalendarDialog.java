package com.example.fengdeyu.xuanyue_reader.dialog;

import android.content.Context;
import android.widget.DatePicker;

import com.example.fengdeyu.xuanyue_reader.R;

/**
 * Created by fengdeyu on 2017/3/20.
 */


public class CalendarDialog extends MyDialog {


    public CalendarDialog(Context context) {
        super(context, R.style.DialogTheme);
        setContentView(R.layout.dialog_calendar);
        setCancelable(false);

    }


}













