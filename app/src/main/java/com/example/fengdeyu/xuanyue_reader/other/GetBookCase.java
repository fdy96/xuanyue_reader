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

        String URL="http://zhannei.baidu.com/cse/search?q="+cnToUnicode(bookTitle)+"&click=1&entry=1&s=8353527289636145615&nsid=";
        try {
            Document doc=Jsoup.connect(URL).get();
            Elements links=doc.select("div.result-item");
            Element link=links.first();



            Elements hrefs=link.select("div[onclick]");
            for(Element href:hrefs){
                String s1=href.attr("onclick");
                String s2=s1.substring(s1.indexOf("'")+1,s1.lastIndexOf("'"));
                String s3=s2.substring(s2.lastIndexOf("/")+1,s2.lastIndexOf("."));
                String s4=s3.substring(0,s3.length()-3);
                String s5="http://www.biquge.com.tw/"+s4+"_"+s3+"/";
                Log.i("info",s5);


                bookItemBean.bookHref=s5;//获取小说章节目录链接
            }

            Elements srcs=link.select("img[src]");
            for(Element src:srcs){
                String s1=src.attr("src");
                bookItemBean.bookIconUrl=s1;//获取小说图片链接

            }
//
            Elements titles=link.select(".result-item-title.result-game-item-title");
            for(Element title:titles){
                String s1=title.text();
                bookItemBean.bookTitle=s1;//获取小说名称

            }

            Elements infos=link.select(".result-game-item-info-tag");
            String s=infos.first().text().trim();
            bookItemBean.bookAuthor=s;//获取小说作者
            bookItemBean.bookContent=infos.get(1).text().trim();//获取小说最新章节

            Log.i("inf0","bookHerf:"+bookItemBean.bookHref+"\nbookIconUrl:"+bookItemBean.bookIconUrl+"\nbookTitle:"+bookItemBean.bookTitle+
                    "\nbooContent:"+bookItemBean.bookContent);

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
                        chapterContentBean.chapter_url="http://www.biquge.com.tw"+href.attr("href");

                        mList.get(pos).mChapterList.add(chapterContentBean);
                    }
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
