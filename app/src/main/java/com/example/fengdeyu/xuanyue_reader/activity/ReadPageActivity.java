package com.example.fengdeyu.xuanyue_reader.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.adapter.PageAdapter;
import com.example.fengdeyu.xuanyue_reader.adapter.PageViewAdapter;
import com.example.fengdeyu.xuanyue_reader.view.PageView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ReadPageActivity extends AppCompatActivity {
    private int mLineCount=22;
    private ArrayList<String> pages=new ArrayList<>();
    private PageView pageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_page);

        pageView= (PageView) findViewById(R.id.pageView);
        String s=getIntent().getStringExtra("contents");

        try {
            pages=split(s,45,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        PageViewAdapter adapter=new PageViewAdapter(ReadPageActivity.this,pages);
        pageView.setAdapter(adapter);

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
