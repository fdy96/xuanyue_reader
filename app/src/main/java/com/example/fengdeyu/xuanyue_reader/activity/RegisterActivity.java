package com.example.fengdeyu.xuanyue_reader.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fengdeyu.xuanyue_reader.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    private EditText name;
    private EditText psd;

    private Button btn_backLogin;
    private Button btn_register;

    String urlAddress = "http://192.168.1.87:8080/XuanyueReaderServer/RegisterServlet";

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
        setContentView(R.layout.activity_register);

        btn_backLogin= (Button) findViewById(R.id.btn_backLogin);

        btn_backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {               //点击返回登录
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                RegisterActivity.this.finish();
            }
        });

        name= (EditText) findViewById(R.id.user_reg);
        psd= (EditText) findViewById(R.id.password_reg);

        btn_register= (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getUrl = urlAddress + "?username=" + cnToUnicode(name.getText().toString()) + "&password=" + cnToUnicode(psd.getText().toString());
                Log.i("info",getUrl);
                new RegisterAsyncTask().execute(getUrl);

            }
        });
    }

    class RegisterAsyncTask extends AsyncTask<String,Void,String> {

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
                try {
                    if (new JSONObject(s).getBoolean("register")) {
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        RegisterActivity.this.finish();

                    } else {
                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(RegisterActivity.this, "连接服务器失败！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
