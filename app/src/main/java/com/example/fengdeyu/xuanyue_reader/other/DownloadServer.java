package com.example.fengdeyu.xuanyue_reader.other;

import android.content.Context;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by fengdeyu on 2016/12/21.
 */

public class DownloadServer {
    private int mBookId;
    private int downFrom;
    private int downTo;

    private Context mContext;
    public int num=0;

    public DownloadServer(int mBookId, int downFrom, int downTo, Context mContext) {
        this.mBookId = mBookId;
        this.downFrom = downFrom;
        this.downTo = downTo;
        this.mContext = mContext;
    }


    public void download(){
        String dirPath=getDirPath();
        File dir=new File(dirPath);
        if(!dir.exists()){
            dir.mkdirs();
        }

        for(int j=downFrom;j<downTo;j++){
            String s=GetBookCase.getInstance().mList.get(mBookId).mChapterList.get(j).chapter_url;
            String fileName=s.substring(s.lastIndexOf("/")+1,s.lastIndexOf("."));

            File file=new File(getDirPath()+"/"+fileName+".txt");
            if(!file.exists()){
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Log.i("info",file.getPath());


            Document doc = null;
            String contents="";
            try {
                doc = Jsoup.connect(s).get();
                String str_contents = doc.getElementById("contents").text();
                StringBuilder sb = new StringBuilder(str_contents);
                int i = 0;
                while (sb.indexOf(" ", i + 3) != -1) {
                    i = sb.indexOf(" ", i + 3);
                    sb.insert(i, "\n");
                }
                contents=sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            FileOutputStream outputStream;
            try {
                outputStream=new FileOutputStream(dirPath+fileName+".txt");
                outputStream.write(contents.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


            GetBookCase.getInstance().mList.get(mBookId).mChapterList.get(j).isDownload=true;
            GetBookCase.getInstance().mList.get(mBookId).mChapterList.get(j).chapter_local_url=dirPath+fileName+".txt";


            num++;
            Log.i("info",num+"");

        }
    }
    public String getDirPath(){
        String s1=GetBookCase.getInstance().mList.get(mBookId).bookHref;
        String s2=s1.substring(0,s1.lastIndexOf("/"));
        String s3=s2.substring(s2.lastIndexOf("/")+1);
        String dirPath=mContext.getFilesDir()+"/"+s3;
        return dirPath;
    }

}
