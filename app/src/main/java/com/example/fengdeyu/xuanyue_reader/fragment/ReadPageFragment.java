package com.example.fengdeyu.xuanyue_reader.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.adapter.PageViewAdapter;
import com.example.fengdeyu.xuanyue_reader.other.ContentsServer;
import com.example.fengdeyu.xuanyue_reader.other.GetBookCase;
import com.example.fengdeyu.xuanyue_reader.other.GetChapterContent;
import com.example.fengdeyu.xuanyue_reader.other.GetPageAttribute;
import com.example.fengdeyu.xuanyue_reader.dialog.LoadingDialog;
import com.example.fengdeyu.xuanyue_reader.other.MyPageInterface;
import com.example.fengdeyu.xuanyue_reader.other.MyReadInterface;
import com.example.fengdeyu.xuanyue_reader.view.PageView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by fengdeyu on 2017/1/14.
 */



public class ReadPageFragment extends Fragment implements MyPageInterface {
    private View mView;
    private MyReadInterface readActivity;

    private int mLineCount;
    private int mLineLen;
    private ArrayList<String> pages=new ArrayList<>();
    private PageView pageView;

    LoadingDialog dialog;



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

        dialog =new LoadingDialog(mView.getContext());


//        pages=new ContentsServer().renderPage(getActivity(),GetPageAttribute.getInstance().contents,"GBK");
//
//        PageViewAdapter adapter=new PageViewAdapter(mView.getContext(),pages,this);
//        pageView.setAdapter(adapter);




    }



    @Override
    public void onResume() {
        super.onResume();


        if(GetPageAttribute.getInstance().isChanged) {
            dataUpdate(0);
            GetPageAttribute.getInstance().isChanged=false;
        }

        if(GetPageAttribute.getInstance().pageParamChanged){
            pages=new ContentsServer().renderPage(getActivity(),GetPageAttribute.getInstance().contents,"GBK");
            PageViewAdapter adapter=new PageViewAdapter(mView.getContext(),pages,ReadPageFragment.this);
            pageView.setAdapter(adapter);

            GetPageAttribute.getInstance().pageParamChanged=false;
        }


    }

    public boolean flag=true;

    public void dataUpdate(final int i){
        dialog.show();
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                pages=new ContentsServer().renderPage(getActivity(),GetPageAttribute.getInstance().contents,"GBK");

                PageViewAdapter adapter=new PageViewAdapter(mView.getContext(),pages,ReadPageFragment.this);
                pageView.setAdapter(adapter);
                flag=true;
                dialog.dismiss();

            }
        };
        new Thread(){
            @Override
            public void run() {
                super.run();
                if(flag) {
                    flag=false;



                    Log.i("info","update");
                    GetChapterContent.getInstance().currentChapter += i;
                    if (GetPageAttribute.getInstance().source.equals("book_case")) {
                        GetBookCase.getInstance().mList.get(GetPageAttribute.getInstance().bookId).currentChapter = GetChapterContent.getInstance().currentChapter;
                        GetPageAttribute.getInstance().contents = new ContentsServer().loadContents(GetPageAttribute.getInstance().bookId);
                        GetPageAttribute.getInstance().chapterName = GetBookCase.getInstance().mList.get(GetPageAttribute.getInstance().bookId).mChapterList.get(GetChapterContent.getInstance().currentChapter).chapter_name;
                    } else if (GetPageAttribute.getInstance().source.equals("book_intro")) {
                        Log.i("love","ss"+GetChapterContent.getInstance().mList.get(0));
                        GetPageAttribute.getInstance().contents = new ContentsServer().loadContents(GetChapterContent.getInstance().mList.get(GetChapterContent.getInstance().currentChapter).chapter_url);
                        GetPageAttribute.getInstance().chapterName = GetChapterContent.getInstance().mList.get(GetChapterContent.getInstance().currentChapter).chapter_name;
                    }
//                    handler.sendEmptyMessage(0);
                    handler.sendEmptyMessageDelayed(0,100);
                }
            }
        }.start();
    }

    public ArrayList<String> split(String text, int length, String encoding) throws UnsupportedEncodingException {
        ArrayList<String> texts = new ArrayList();
        //String temp = "   ";
        String temp = "";
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
                    //temp += "  ";
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

    @Override
    public void updateData() {
        PageViewAdapter adapter=new PageViewAdapter(mView.getContext(),pages,this);
        pageView.setAdapter(adapter);
    }
}
