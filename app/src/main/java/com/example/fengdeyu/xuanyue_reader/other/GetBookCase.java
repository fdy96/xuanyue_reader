package com.example.fengdeyu.xuanyue_reader.other;

import android.util.Log;
import android.widget.Toast;

import com.example.fengdeyu.xuanyue_reader.bean.BookItemBean;
import com.example.fengdeyu.xuanyue_reader.bean.ChapterContentBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengdeyu on 2016/12/14.
 */

public class GetBookCase {
    public List<BookItemBean> mList=new ArrayList<>();

    private static GetBookCase getBookCase=null;

    public static GetBookCase getInstance(){
        if(getBookCase==null){
            getBookCase=new GetBookCase();
        }
        return getBookCase;
    }

    public void addBook(BookItemBean bookItemBean){
        boolean flag=true;
        for(int i=0;i<mList.size();i++){
            if(bookItemBean.bookTitle.equals(mList.get(i).bookTitle)&&bookItemBean.bookAuthor.equals(mList.get(i).bookAuthor)){
                flag=false;
            }
        }
        if(flag){
            mList.add(bookItemBean);
        }
    }
    public boolean hasBook(BookItemBean bookItemBean){

        for(int i=0;i<mList.size();i++){
            if(bookItemBean.bookTitle.equals(mList.get(i).bookTitle)&&bookItemBean.bookAuthor.equals(mList.get(i).bookAuthor)){
                return true;
            }
        }
        return false;
    }

    public void loadChapterContent(int pos, String URL) {
        //TODO:从网络上获取章节列表

        try {
            Document doc = Jsoup.connect(URL).get();
            Elements links = doc.select("td.L");
            for (Element link : links) {
                ChapterContentBean chapterContentBean = new ChapterContentBean();

                Elements hrefs = link.select("a[href]");
                for (Element href : hrefs) {

                    if (!link.text().equals("")) {
                        chapterContentBean.chapter_name = link.text();
                        chapterContentBean.chapter_url =href.attr("href");

                        mList.get(pos).mChapterList.add(chapterContentBean);
                    }
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
