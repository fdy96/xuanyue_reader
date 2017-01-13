package com.example.fengdeyu.xuanyue_reader.other;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fengdeyu.xuanyue_reader.R;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TestPagingActivity extends AppCompatActivity {
    private int mLineCount=22;
    private ArrayList<String> page=new ArrayList<>();
    private TextView textView;

    private Button button2,button3;
    private int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_paging);

        String s=getIntent().getStringExtra("contents");

        try {
            page=split(s,45,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        textView= (TextView) findViewById(R.id.textView);
        textView.setText(page.get(i));

        button2= (Button) findViewById(R.id.button2);
        button3= (Button) findViewById(R.id.button3);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(page.get(--i));
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(page.get(++i));
            }
        });

//
//        int num=s.length()/3;

//        String s1=s;
//
//        s1.substring(0,num);
//
//        String s2=s;
//        s2.substring(num,num*2);
//
//
//        String s3=s.substring(num*2);
//
//        Log.i("info",num+"");
//        Log.i("info1",s.substring(76,83));
//        Log.i("info",s.length()+"");
////        Log.i("info1",s1);
////        Log.i("info2",s2);
//        Log.i("info3",s3);
    }

    public ArrayList<String> split(String text, int length, String encoding) throws UnsupportedEncodingException {
        ArrayList<String> texts = new ArrayList();
        String temp = "    ";
        String c;
        int lines = 0;
        int pos = -2;
        int startInd = 0;
        for (int i = 0; text != null && i < text.length(); ) {
            byte[] b = String.valueOf(text.charAt(i)).getBytes(encoding);
            pos += b.length;
            if (pos >= length) {
                int endInd;
                if (pos == length) {
                    endInd = ++i;
                } else {
                    endInd = i;
                }
                temp += text.substring(startInd, endInd)+"\n"; // 加入一行
                lines++;
                if (lines >= mLineCount) { // 超出一页
                    texts.add(temp); // 加入
                    temp = "";
                    lines = 0;
                }
                pos = 0;
                startInd = i;
            } else {
                c = new String(b, encoding);
                if (c.equals("\n")) {
                    temp += text.substring(startInd, i + 1);
                    lines++;
                    if (lines >= mLineCount) {
                        texts.add(temp);
                        temp = "   ";
                        lines = 0;
                    }
                    temp += "   ";
                    pos = -2;
                    startInd = i +1;
                }
                i++;
            }
        }
        if (startInd < text.length()) {
            temp += text.substring(startInd);
            lines++;
        }
        if (!TextUtils.isEmpty(temp))
            texts.add(temp);
        return texts;
    }
}
