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

    public boolean addBook(BookItemBean bookItemBean){
        boolean flag=true;
        for(int i=0;i<mList.size();i++){
            if(bookItemBean.bookTitle.equals(mList.get(i).bookTitle)&&bookItemBean.bookAuthor.equals(mList.get(i).bookAuthor)){
                flag=false;
            }
        }
        if(flag){
            mList.add(bookItemBean);
        }
        return flag;
    }
    public boolean hasBook(BookItemBean bookItemBean){

        for(int i=0;i<mList.size();i++){
            if(bookItemBean.bookTitle.equals(mList.get(i).bookTitle)&&bookItemBean.bookAuthor.equals(mList.get(i).bookAuthor)){
                return true;
            }
        }
        return false;
    }

    public BookItemBean addBookByTitle(String bookTitle){
        BookItemBean bookItemBean=new BookItemBean();

        String URL="http://www.ltsw888.com/s.php?ie=gbk&q="+bookTitle;
        try {
            Document doc=Jsoup.connect(URL).get();
            Elements links=doc.select("div.bookbox");
            Element link=links.first();

            Elements titles=link.select("h4.bookname");
            for(Element title:titles){
                String s1=title.text();
                bookItemBean.bookTitle=s1;//获取小说名称
                String href="http://www.ltsw888.com"+title.select("a").get(0).attr("href");
                bookItemBean.bookHref=href;//获取小说链接
            }

            Elements imgs=link.select("img");
            for(Element img:imgs){
                String s1="http://www.ltsw888.com"+img.attr("src");
                bookItemBean.bookIconUrl=s1;//获取小说封面
            }

            Elements authors=link.select("div.author");
            for(Element author:authors){
                String s1=author.text();
                bookItemBean.bookAuthor=s1;//获取小说作者
            }


            Elements contents=link.select("div.update");
            for(Element content:contents){
                String s1=content.text();
                bookItemBean.bookContent=s1;//获取小说最新章节
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return bookItemBean;
    }

    public String cnToUnicode(String str){
        String result="";
        for (int i = 0; i < str.length(); i++){
            int chr1 = (char) str.charAt(i);
            if(chr1>=19968&&chr1<=171941){//汉字范围 \u4e00-\u9fa5 (中文)
                result+="\\u" + Integer.toHexString(chr1);
            }else{
                result+=str.charAt(i);
            }
        }
        return result;
    }


    public void loadChapterContent(int pos, String URL) {
        //TODO:从网络上获取章节列表

        try {
            Document doc = Jsoup.connect(URL).get();
            Elements links = doc.select("dd");
            for (Element link : links) {
                ChapterContentBean chapterContentBean = new ChapterContentBean();

                Elements hrefs = link.select("a[href]");
                for (Element href : hrefs) {

                    if (!link.text().equals("")) {
                        chapterContentBean.chapter_name = link.text();
                        chapterContentBean.chapter_url="http://www.ltsw888.com"+href.attr("href");

                        mList.get(pos).mChapterList.add(chapterContentBean);
                    }
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
