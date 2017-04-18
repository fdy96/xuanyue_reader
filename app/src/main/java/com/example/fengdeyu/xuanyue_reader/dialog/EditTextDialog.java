package com.example.fengdeyu.xuanyue_reader.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.activity.SettingActivity;

/**
 * Created by fengdeyu on 2017/3/20.
 */


public class EditTextDialog extends MyDialog {

    public EditTextDialog(Context context) {
        super(context, R.style.DialogTheme);
        setContentView(R.layout.dialog_edit_text);
        setCancelable(false);
    }


}













