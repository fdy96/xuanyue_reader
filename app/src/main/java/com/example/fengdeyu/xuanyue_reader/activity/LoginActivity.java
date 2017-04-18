package com.example.fengdeyu.xuanyue_reader.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.other.DateCleanServer;
import com.example.fengdeyu.xuanyue_reader.other.GetPageAttribute;
import com.example.fengdeyu.xuanyue_reader.other.GetUserInfo;

import org.json.JSONException;
import org.json.JSONObject;
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

    private TextView tv_forget;

    String urlAddress = "http://10.0.2.2:8080/XuanyueReaderServer/LoginServlet";

    public String cnToUnicode(String str){
        String result="";
        for (int i = 0; i < str.length(); i++){
            int chr1 = (char) str.charAt(i);
            if(chr1>=19968&&chr1<=171941){//汉字范围 \u4e00-\u9fa5 (中文)
                result+="\\u" + Integer.toHexString(chr1);
            }else{
                result+=str.charAt(i);
            }
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login= (Button) findViewById(R.id.btn_login);
        btn_sign= (Button) findViewById(R.id.btn_sign);

        tv_forget= (TextView) findViewById(R.id.tv_foget);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {               //点击登录按钮跳转到主界面
                String getUrl = urlAddress + "?username=" + cnToUnicode(name.getText().toString()) + "&password=" + cnToUnicode(psd.getText().toString());
                Log.i("info",getUrl);
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


        tv_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(LoginActivity.this);
                dialog.setMessage("请联系客服QQ：827693352找回密码/(ㄒoㄒ)/~~");
                dialog.setPositiveButton("确定",null);
                dialog.setNegativeButton("取消",null);

                dialog.show();
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
                return doc.text();
            } catch (IOException e) {
                e.printStackTrace();
            }



            return "404";

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(!s.equals("404")) {

                JSONObject loginInfo;
                try {
                    loginInfo = new JSONObject(s);
                    GetUserInfo.getInstance().sign = loginInfo.getBoolean("login");

                    if (GetUserInfo.getInstance().sign) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                        GetUserInfo.getInstance().getInfoByJSON(loginInfo);


                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        LoginActivity.this.finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else {
                Toast.makeText(LoginActivity.this, "连接服务器失败！", Toast.LENGTH_SHORT).show();
            }



        }
    }
}
