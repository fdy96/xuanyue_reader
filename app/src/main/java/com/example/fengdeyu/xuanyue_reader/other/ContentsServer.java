package com.example.fengdeyu.xuanyue_reader.other;

import android.content.Context;
import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by fengdeyu on 2017/2/22.
 */

public class ContentsServer {
    /**
     * 根据bookId读取内容
     * @param bookId
     * @return
     */
    public String loadContents(int bookId){
        String contents="";
        String url="";
        if (GetBookCase.getInstance().mList.get(bookId).mChapterList.get(GetChapterContent.getInstance().currentChapter).isDownload) {
            url = GetBookCase.getInstance().mList.get(bookId).mChapterList.get(GetChapterContent.getInstance().currentChapter).chapter_local_url;
        } else {
            url = GetBookCase.getInstance().mList.get(bookId).mChapterList.get(GetChapterContent.getInstance().currentChapter).chapter_url;
        }

        if(GetBookCase.getInstance().mList.get(bookId).mChapterList.get(GetChapterContent.getInstance().currentChapter).isDownload){
            contents=read(url);
        }else {
            contents=loadContents(url);
        }




        return contents;
    }

    /**
     * 根据url读取内容
     * @param url
     * @return
     */
    public String loadContents(String url){
        String contents="";
        Document doc= null;
        try {
            doc = Jsoup.connect(url).get();
            contents=doc.getElementById("content").text();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder(contents);
        int i = 0;
        while (sb.indexOf(" ", i + 3) != -1) {
            i = sb.indexOf(" ", i + 3);
            sb.insert(i, "\n");
        }


        contents=sb.toString();


        return contents;
    }

    /**
     * 读取本地文件
     * @param url
     * @return
     */
    private String read(String url) {
        try {
            FileInputStream inputStream=new FileInputStream(url);
            byte[] bytes = new byte[1024];
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            while (inputStream.read(bytes) != -1) {
                arrayOutputStream.write(bytes, 0, bytes.length);
            }
            inputStream.close();
            arrayOutputStream.close();
            String contents = new String(arrayOutputStream.toByteArray());

            return contents;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 获得页面
     * @param context
     * @return
     */
    public ArrayList<String> renderPage(Context context,String contents,String encoding){
        int mHeight=context.getResources().getDisplayMetrics().heightPixels;
        int mWidth=context.getResources().getDisplayMetrics().widthPixels;

        int marginWidth = (int)(20*context.getResources().getDisplayMetrics().density+0.5f);
        int marginHeight = (int)(20*context.getResources().getDisplayMetrics().density+0.5f);
        int mFontSize = (int)((GetPageAttribute.getInstance().textSize*context.getResources().getDisplayMetrics().scaledDensity)+0.5f);
        int mLineSpace = mFontSize / GetPageAttribute.getInstance().linespace;


        int mVisibleHeight = mHeight-marginHeight*2-mLineSpace*2;
        int mVisibleWidth = mWidth-marginWidth*2 ;

        int mLineCount = mVisibleHeight / (mFontSize+mLineSpace);
        int mLineLen = (mVisibleWidth*2)/mFontSize;

        return split(contents,mLineCount,mLineLen,encoding);
    }

    /**
     * 分页算法
     * @param contents
     * @param lineCount
     * @param lineLen
     * @param encoding
     * @return
     */
    public ArrayList<String> split(String contents,int lineCount,int lineLen,String encoding){
        ArrayList<String> texts = new ArrayList();
        String temp = "   ";
        String c;
        int lines = 0;
        int pos = 2;
        int startInd = 0;
        int endInd=0;
        for (int i = 0; contents != null && i < contents.length(); ) {
            byte[] b = new byte[0];
            try {
                b = String.valueOf(contents.charAt(i)).getBytes(encoding);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            pos += b.length;
            if (pos > lineLen) {

                endInd=i;
                if(String.valueOf(contents.charAt(i)).equals("\n")){
                    temp += contents.substring(startInd, endInd);
                }else {
                    temp += contents.substring(startInd, endInd) + "\n"; // 加入一行
                    lines++;
                }
                if (lines >= lineCount) { // 超出一页
                    texts.add(temp); // 加入
                    temp = "";
                    lines = 0;
                }
                pos = 2;
                startInd = i;
            } else {
                try {
                    c = new String(b, encoding);
                    if (c.equals("\n")) {

                        temp += contents.substring(startInd, i + 1);
                        lines++;
                        if (lines >= lineCount) {
                            texts.add(temp);
                            temp = "";
                            lines = 0;
                        }
                        temp += "  ";
                        pos = 1;
                        startInd = i +1;
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                i++;
            }
        }
        if (startInd < contents.length()) {
            temp += contents.substring(startInd);
            lines++;
        }
        if (!TextUtils.isEmpty(temp))
            texts.add(temp);
        return texts;

    }

}
