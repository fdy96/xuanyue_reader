package com.example.fengdeyu.xuanyue_reader.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.activity.ReadActivity;
import com.example.fengdeyu.xuanyue_reader.activity.ReadLocalBookActivity;
import com.example.fengdeyu.xuanyue_reader.adapter.PageViewAdapter;
import com.example.fengdeyu.xuanyue_reader.other.GetPageAttribute;
import com.example.fengdeyu.xuanyue_reader.other.MyReadInterface;
import com.example.fengdeyu.xuanyue_reader.view.PageView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by fengdeyu on 2017/1/14.
 */



public class ReadPageFragment extends Fragment {
    private View mView;
    private MyReadInterface readActivity;

    private int mLineCount;
    private int mLineLen;
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


        readActivity= (MyReadInterface) getActivity();


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


        int mHeight=getActivity().getResources().getDisplayMetrics().heightPixels;
        int mWidth=getActivity().getResources().getDisplayMetrics().widthPixels;

        int marginWidth = (int)(20*getActivity().getResources().getDisplayMetrics().density+0.5f);
        int marginHeight = (int)(20*getActivity().getResources().getDisplayMetrics().density+0.5f);
        int mFontSize = (int)((GetPageAttribute.getInstance().textSize*getActivity().getResources().getDisplayMetrics().scaledDensity)+0.5f);
        int mLineSpace = mFontSize / GetPageAttribute.getInstance().linespace;


        int mVisibleHeight = mHeight-marginHeight*2-mLineSpace*2;
        int mVisibleWidth = mWidth-marginWidth*2 ;


        mLineCount = mVisibleHeight / (mFontSize+mLineSpace);
        mLineLen = (mVisibleWidth*2)/mFontSize;

        String s= GetPageAttribute.getInstance().contents;

        try {
            pages=split(s,mLineLen,"GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        PageViewAdapter adapter=new PageViewAdapter(mView.getContext(),pages);
        pageView.setAdapter(adapter);






    }

    public ArrayList<String> split(String text, int length, String encoding) throws UnsupportedEncodingException {
        ArrayList<String> texts = new ArrayList();
        String temp = "   ";
        String c;
        int lines = 0;
        int pos = 2;
        int startInd = 0;
        int endInd=0;
        boolean tabLine=false;
        for (int i = 0; text != null && i < text.length(); ) {
            byte[] b = String.valueOf(text.charAt(i)).getBytes(encoding);
            pos += b.length;
            if (pos > length) {




                endInd=i;
                if(String.valueOf(text.charAt(i)).equals("\n")){
                    temp += text.substring(startInd, endInd);
                }else {
                    temp += text.substring(startInd, endInd) + "\n"; // 加入一行
                    lines++;
                }




                if (lines >= mLineCount) { // 超出一页
                    texts.add(temp); // 加入
                    temp = "";
                    lines = 0;
                }
                pos = 2;
                startInd = i;
            } else {
                c = new String(b, encoding);
                if (c.equals("\n")) {

                    temp += text.substring(startInd, i + 1);
                    lines++;
                    if (lines >= mLineCount) {
                        texts.add(temp);
                        temp = "";
                        lines = 0;
                    }
                    temp += "  ";
                    pos = 1;
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
