package com.example.fengdeyu.xuanyue_reader.other;

import android.widget.Toast;

import com.example.fengdeyu.xuanyue_reader.bean.BookItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengdeyu on 2016/12/14.
 */

public class GetBookCase {
    public List<BookItemBean> mList=new ArrayList<>();
    public int currentChapter=0;

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

}
