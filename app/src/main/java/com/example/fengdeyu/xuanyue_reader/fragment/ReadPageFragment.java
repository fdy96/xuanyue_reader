package com.example.fengdeyu.xuanyue_reader.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.activity.ReadActivity;
import com.example.fengdeyu.xuanyue_reader.activity.ReadPageActivity;
import com.example.fengdeyu.xuanyue_reader.adapter.PageViewAdapter;
import com.example.fengdeyu.xuanyue_reader.view.PageView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by fengdeyu on 2017/1/14.
 */



public class ReadPageFragment extends Fragment {
    private View mView;
    private ReadActivity readActivity;

    private int mLineCount=22;
    private ArrayList<String> pages=new ArrayList<>();
    private PageView pageView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView=inflater.inflate(R.layout.fragment_read_page,container,false);
        return mView;


    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        readActivity= (ReadActivity) getActivity();

        pageView= (PageView) mView.findViewById(R.id.pageView);


        pageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pageView.isClickCenter) {
                    readActivity.toggleReadBar();
                    pageView.isClickCenter = false;
                }
            }
        });




    }



    @Override
    public void onResume() {
        super.onResume();
        String s=readActivity.mContents;

        try {
            pages=split(s,45,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        PageViewAdapter adapter=new PageViewAdapter(mView.getContext(),pages);
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
