package com.example.fengdeyu.xuanyue_reader.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.other.GetUserInfo;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button_1;
    private Button button_2;
    private Button button_3;
    private Button button_4;

    private EditText et_describe;
    private EditText et_contact;

    private Button btn_submit;

    private String contents;

    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        button_1= (Button) findViewById(R.id.button_1);
        button_2= (Button) findViewById(R.id.button_2);
        button_3= (Button) findViewById(R.id.button_3);
        button_4= (Button) findViewById(R.id.button_4);

        et_describe= (EditText) findViewById(R.id.et_describe);
        et_contact= (EditText) findViewById(R.id.et_contact);

        btn_submit= (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Map<String,Object> feedback=new HashMap<String, Object>();

                feedback.put("uid", GetUserInfo.getInstance().userInfo.uid);

                String type="";
                contents="问题：\n";
                if(button_1.getTag().toString().equals("true")){
                    type+="书籍无法阅读、";
                }
                if(button_2.getTag().toString().equals("true")){
                    type+="空章乱码、";
                }
                if(button_3.getTag().toString().equals("true")){
                    type+="重复内容或章节出错、";
                }
                if(button_4.getTag().toString().equals("true")){
                    type+="缓存失败、";
                }
                feedback.put("type",type);

                feedback.put("details",et_describe.getText().toString());

                feedback.put("contact",et_contact.getText().toString());


                Toast.makeText(FeedbackActivity.this,new JSONObject(feedback).toString(),Toast.LENGTH_SHORT).show();

                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        String url="http://192.168.1.87:8080/XuanyueReaderServer/FeedbackServlet";
                        Connection conn= Jsoup.connect(url);

                        conn.data("feedback",new JSONObject(feedback).toString());

                        try {
                            conn.post();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
            }
        });

        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);

        button_1.setTag("false");
        button_2.setTag("false");
        button_3.setTag("false");
        button_4.setTag("false");
    }

    @Override
    public void onClick(View view) {
        if(view.getTag().toString().equals("false")){
            view.setBackgroundColor(0xffe8e8e8);
            view.setTag("true");
        }else {
            view.setBackgroundColor(0xffc8c8c8);
            view.setTag("false");
        }
    }
}
