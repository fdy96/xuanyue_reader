package com.example.fengdeyu.xuanyue_reader.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.other.GetPageAttribute;

public class TypefaceSelectActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rl_typeface_1;
    private RelativeLayout rl_typeface_2;
    private RelativeLayout rl_typeface_3;

    private ImageView iv_select_1;
    private ImageView iv_select_2;
    private ImageView iv_select_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typeface_select);

        rl_typeface_1= (RelativeLayout) findViewById(R.id.rl_typeface_1);
        rl_typeface_2= (RelativeLayout) findViewById(R.id.rl_typeface_2);
        rl_typeface_3= (RelativeLayout) findViewById(R.id.rl_typeface_3);

        iv_select_1= (ImageView) findViewById(R.id.iv_select_1);
        iv_select_2= (ImageView) findViewById(R.id.iv_select_2);
        iv_select_3= (ImageView) findViewById(R.id.iv_select_3);

        rl_typeface_1.setOnClickListener(this);
        rl_typeface_2.setOnClickListener(this);
        rl_typeface_3.setOnClickListener(this);


        switch (GetPageAttribute.getInstance().font_typeface){
            case "FZXH.TTF":
                iv_select_1.setBackgroundResource(R.mipmap.ic_checkbox_selected);
                break;
            case "FZYBKS.TTF":
                iv_select_2.setBackgroundResource(R.mipmap.ic_checkbox_selected);
                break;
            case "HYBS.ttf":
                iv_select_3.setBackgroundResource(R.mipmap.ic_checkbox_selected);
                break;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_typeface_1:
                GetPageAttribute.getInstance().font_typeface="FZXH.TTF";
                GetPageAttribute.getInstance().linespace=20;
                GetPageAttribute.getInstance().pageParamChanged=true;
                iv_select_1.setBackgroundResource(R.mipmap.ic_checkbox_selected);
                iv_select_2.setBackgroundResource(R.mipmap.ic_checkbox_normal);
                iv_select_3.setBackgroundResource(R.mipmap.ic_checkbox_normal);
                break;
            case R.id.rl_typeface_2:
                GetPageAttribute.getInstance().font_typeface="FZYBKS.TTF";
                GetPageAttribute.getInstance().linespace=7;
                GetPageAttribute.getInstance().pageParamChanged=true;
                iv_select_2.setBackgroundResource(R.mipmap.ic_checkbox_selected);
                iv_select_1.setBackgroundResource(R.mipmap.ic_checkbox_normal);
                iv_select_3.setBackgroundResource(R.mipmap.ic_checkbox_normal);
                break;
            case R.id.rl_typeface_3:
                GetPageAttribute.getInstance().font_typeface="HYBS.ttf";
                GetPageAttribute.getInstance().linespace=20;
                GetPageAttribute.getInstance().pageParamChanged=true;
                iv_select_3.setBackgroundResource(R.mipmap.ic_checkbox_selected);
                iv_select_1.setBackgroundResource(R.mipmap.ic_checkbox_normal);
                iv_select_2.setBackgroundResource(R.mipmap.ic_checkbox_normal);
                break;
        }
    }
}
