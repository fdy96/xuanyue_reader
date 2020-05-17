package com.example.fengdeyu.xuanyue_reader.other;

import android.os.AsyncTask;
import android.util.Log;

import com.example.fengdeyu.xuanyue_reader.bean.ChapterContentBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengdeyu on 2016/12/13.
 */

public class GetChapterContent {
    public List<ChapterContentBean> mList=new ArrayList<>();
    public int currentChapter=0;
    public String bookTitle;

    private static GetChapterContent getChapterContent=null;

    public static GetChapterContent getInstance(){
        if(getChapterContent==null){
            getChapterContent=new GetChapterContent();
        }
        return getChapterContent;
    }


    public void loadChapterContent(String URL){

        clear();
        int i=0;
        //TODO:从网络上获取章节列表

        try {
            Document doc= Jsoup.connect(URL).get();

            Elements links=doc.select("dd");
            for(Element link:links){

                ChapterContentBean chapterContentBean=new ChapterContentBean();

                Elements hrefs=link.select("a[href]");
                for(Element href:hrefs){
                    chapterContentBean.chapter_name = href.text();
                    chapterContentBean.chapter_url="http://www.ltsw888.com"+href.attr("href");

                    mList.add(chapterContentBean);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void clear(){
        mList.clear();
        currentChapter=0;
    }

}
