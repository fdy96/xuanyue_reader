package com.example.fengdeyu.xuanyue_reader.other;

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

    private static GetChapterContent getChapterContent=null;

    public static GetChapterContent getInstance(){
        if(getChapterContent==null){
            getChapterContent=new GetChapterContent();
        }
        return getChapterContent;
    }


    public void loadChapterContent(String URL){
        //TODO:从网络上获取章节列表
        URL="http://www.23us.so/files/article/html/10/10674/index.html";

        try {
            Document doc= Jsoup.connect(URL).get();
            Elements links=doc.select("td.L");
            for(Element link:links){
                ChapterContentBean chapterContentBean=new ChapterContentBean();
                chapterContentBean.chapter_name=link.text();
                chapterContentBean.chapter_url=link.attr("href");
                mList.add(chapterContentBean);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
