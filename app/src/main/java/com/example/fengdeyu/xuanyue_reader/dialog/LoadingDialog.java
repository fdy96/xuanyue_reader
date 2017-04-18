package com.example.fengdeyu.xuanyue_reader.dialog;

import android.app.Dialog;
import android.content.Context;

import com.example.fengdeyu.xuanyue_reader.R;

/**
 * Created by fengdeyu on 2017/2/27.
 */

public class LoadingDialog extends Dialog {
    public LoadingDialog(Context context) {
        super(context, R.style.LoadingDialogTheme);
        setContentView(R.layout.dialog_loading);
        setCanceledOnTouchOutside(false);
    }
}
