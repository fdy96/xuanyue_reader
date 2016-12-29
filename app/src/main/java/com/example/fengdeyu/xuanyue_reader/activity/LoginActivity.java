package com.example.fengdeyu.xuanyue_reader.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fengdeyu.xuanyue_reader.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private EditText name;
    private EditText psd;

    private Button btn_login;
    private Button btn_sign;

    String urlAddress = "http://10.128.228.39:8080/LoginAndRegister/LoginServlet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login= (Button) findViewById(R.id.btn_login);
        btn_sign= (Button) findViewById(R.id.btn_sign);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {               //点击登录按钮跳转到主界面
                String getUrl = urlAddress + "?username=" + name.getText().toString() + "&password=" + psd.getText().toString();
                new LoginAsyncTask().execute(getUrl);


            }
        });

        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {              //点击注册按钮跳转到注册界面
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                LoginActivity.this.finish();
            }
        });


        name= (EditText) findViewById(R.id.user);
        psd= (EditText) findViewById(R.id.password);






    }

    class LoginAsyncTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            Document doc= null;
            try {
                doc = Jsoup.connect(params[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return doc.text();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("true")){
                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();

                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                LoginActivity.this.finish();

            }else{
                Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
