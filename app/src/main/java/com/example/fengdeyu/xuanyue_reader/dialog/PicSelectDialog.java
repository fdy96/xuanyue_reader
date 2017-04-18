package com.example.fengdeyu.xuanyue_reader.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.fengdeyu.xuanyue_reader.R;

/**
 * Created by fengdeyu on 2017/3/21.
 */

public class PicSelectDialog extends MyDialog implements View.OnClickListener {
    private ImageView iv_pic_1;
    private ImageView iv_pic_2;
    private ImageView iv_pic_3;

    public PicSelectDialog(Context context) {
        super(context, R.style.DialogTheme);
        setContentView(R.layout.dialog_pic_select);
        setCancelable(false);

        initView();
    }

    private void initView() {
        iv_pic_1= (ImageView) findViewById(R.id.iv_pic_1);
        iv_pic_2= (ImageView) findViewById(R.id.iv_pic_2);
        iv_pic_3= (ImageView) findViewById(R.id.iv_pic_3);

        iv_pic_1.setOnClickListener(this);
        iv_pic_2.setOnClickListener(this);
        iv_pic_3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_pic_1:
                iv_pic_1.setImageResource(R.mipmap.ic_pic_select);
                iv_pic_2.setImageResource(0);
                iv_pic_3.setImageResource(0);
                setPic_num(1);
                break;
            case R.id.iv_pic_2:
                iv_pic_1.setImageResource(0);
                iv_pic_2.setImageResource(R.mipmap.ic_pic_select);
                iv_pic_3.setImageResource(0);
                setPic_num(2);
                break;
            case R.id.iv_pic_3:
                iv_pic_1.setImageResource(0);
                iv_pic_2.setImageResource(0);
                iv_pic_3.setImageResource(R.mipmap.ic_pic_select);
                setPic_num(3);
                break;
        }
    }
}
