package com.example.fengdeyu.xuanyue_reader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.fengdeyu.xuanyue_reader.R;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_backLogin;

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
    }
}
