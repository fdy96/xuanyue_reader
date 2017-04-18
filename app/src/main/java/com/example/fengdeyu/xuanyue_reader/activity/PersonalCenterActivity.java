package com.example.fengdeyu.xuanyue_reader.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.bean.UserInfoBean;
import com.example.fengdeyu.xuanyue_reader.dialog.CalendarDialog;
import com.example.fengdeyu.xuanyue_reader.dialog.EditTextDialog;
import com.example.fengdeyu.xuanyue_reader.dialog.MyDialog;
import com.example.fengdeyu.xuanyue_reader.dialog.PicSelectDialog;
import com.example.fengdeyu.xuanyue_reader.dialog.SexSelectDialog;
import com.example.fengdeyu.xuanyue_reader.other.GetBookCase;
import com.example.fengdeyu.xuanyue_reader.other.GetUserInfo;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonalCenterActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_nickname;
    private TextView tv_uid;
    private TextView tv_sex;
    private TextView tv_birthday;
    private TextView tv_signature;

    private ImageView iv_head_portrait;

    private TextView tv_logout;

    private TextView tv_update;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);

        tv_logout= (TextView) findViewById(R.id.tv_logout);
        tv_logout.setOnClickListener(this);

        tv_update= (TextView) findViewById(R.id.tv_update);
        tv_update.setOnClickListener(this);

        iv_head_portrait= (ImageView) findViewById(R.id.iv_head_portrait);
        switch (GetUserInfo.getInstance().userInfo.head_portrait){
            case "1":
                iv_head_portrait.setBackgroundResource(R.mipmap.head_portrait_1);
                iv_head_portrait.setTag(1);
                break;
            case "2":
                iv_head_portrait.setBackgroundResource(R.mipmap.head_portrait_2);
                iv_head_portrait.setTag(2);
                break;
            case "3":
                iv_head_portrait.setBackgroundResource(R.mipmap.head_portrait_3);
                iv_head_portrait.setTag(3);
                break;
        }


        tv_nickname= (TextView) findViewById(R.id.tv_nickname);
        tv_uid= (TextView) findViewById(R.id.tv_uid);
        tv_sex= (TextView) findViewById(R.id.tv_sex);
        tv_birthday= (TextView) findViewById(R.id.tv_birthday);
        tv_signature= (TextView) findViewById(R.id.tv_signature);

        tv_nickname.setText(GetUserInfo.getInstance().userInfo.nickname);
        tv_uid.setText(GetUserInfo.getInstance().userInfo.uid);
        tv_sex.setText(GetUserInfo.getInstance().userInfo.sex);
        tv_birthday.setText(GetUserInfo.getInstance().userInfo.birthday);
        tv_signature.setText(GetUserInfo.getInstance().userInfo.signature);



    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_logout:
                GetUserInfo.getInstance().toLogout();
                onBackPressed();
                break;

            case R.id.tv_update:
                final UserInfoBean userInfoBean=new UserInfoBean();
                userInfoBean.head_portrait=iv_head_portrait.getTag().toString();
                userInfoBean.nickname=tv_nickname.getText().toString();
                userInfoBean.uid=tv_uid.getText().toString();
                userInfoBean.birthday=tv_birthday.getText().toString();
                userInfoBean.sex=tv_sex.getText().toString();
                userInfoBean.signature=tv_signature.getText().toString();
                Gson gson=new Gson();
                Log.i("json",gson.toJson(userInfoBean));

                final Handler handler=new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);

                        switch (msg.what){
                            case 0:
                                Toast.makeText(PersonalCenterActivity.this,"修改失败",Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                GetUserInfo.getInstance().userInfo=userInfoBean;
                                Toast.makeText(PersonalCenterActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                                break;
                        }

                    }
                };

                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        String flag="false";
                        String url="http://10.0.2.2:8080/XuanyueReaderServer/UpdateUserInfoServlet";
                        Connection conn= Jsoup.connect(url);

                        conn.data("user_info",new Gson().toJson(userInfoBean));

                        try {
                            Document doc=conn.post();
                            flag=doc.text();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if(flag.equals("true")){
                            handler.sendEmptyMessageDelayed(1,10);
                        }else {
                            handler.sendEmptyMessageDelayed(0,10);

                        }


                    }
                }.start();



                break;



        }
    }

    private MyDialog dialog;

    public void onClickItem(View v){
        switch (v.getId()){
            case R.id.rl_nickname:
                dialog=new EditTextDialog(this);
                dialog.getTextView(R.id.tv_title).setText("修改用户名");
                dialog.show();
                changeLayoutAlpha(0.6f);
                break;
            case R.id.rl_signature:
                dialog=new EditTextDialog(this);
                dialog.getTextView(R.id.tv_title).setText("修改个性签名");
                dialog.show();
                changeLayoutAlpha(0.6f);
                break;
            case R.id.rl_head_portrait:
                dialog=new PicSelectDialog(this);
                dialog.show();
                changeLayoutAlpha(0.6f);
                break;
            case R.id.rl_sex:
                dialog=new SexSelectDialog(this);
                dialog.show();
                changeLayoutAlpha(0.6f);
                break;
            case R.id.rl_birthday:
                dialog=new CalendarDialog(this);
                dialog.show();
                changeLayoutAlpha(0.6f);
                break;
        }
    }
    public void onClickDialog(View v){
        switch (v.getId()) {
            case R.id.btn_no:
                dialog.dismiss();
                changeLayoutAlpha(1f);
                break;
            case R.id.btn_ok_2:
                if(dialog.getTextView(R.id.tv_title).getText().toString().equals("修改用户名")){
                    tv_nickname.setText(dialog.getEditText(R.id.edit_text).getText().toString());
                }
                if(dialog.getTextView(R.id.tv_title).getText().toString().equals("修改个性签名")){
                    tv_signature.setText(dialog.getEditText(R.id.edit_text).getText().toString());
                }

                dialog.dismiss();
                changeLayoutAlpha(1f);
                break;
            case R.id.btn_ok_1:
                switch (dialog.getPic_num()+""){
                    case "1":
                        iv_head_portrait.setBackgroundResource(R.mipmap.head_portrait_1);
                        iv_head_portrait.setTag(1);
                        break;
                    case "2":
                        iv_head_portrait.setBackgroundResource(R.mipmap.head_portrait_2);
                        iv_head_portrait.setTag(2);
                        break;
                    case "3":
                        iv_head_portrait.setBackgroundResource(R.mipmap.head_portrait_3);
                        iv_head_portrait.setTag(3);
                        break;
                }
                dialog.dismiss();
                changeLayoutAlpha(1f);
                break;
            case R.id.btn_ok_3:
                switch (dialog.getPic_num()+""){
                    case "1":
                        tv_sex.setText("男");
                        break;
                    case "2":
                        tv_sex.setText("女");
                        break;
                }
                dialog.dismiss();
                changeLayoutAlpha(1f);
                break;
            case R.id.btn_ok_4:
                DatePicker datePicker=dialog.getDatePicker(R.id.date_picker);
                String date=datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth();
                tv_birthday.setText(date);

                dialog.dismiss();
                changeLayoutAlpha(1f);
                break;

        }
    }

    private void changeLayoutAlpha(float alpha){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    public void onBack(View v){
        onBackPressed();
    }
}
