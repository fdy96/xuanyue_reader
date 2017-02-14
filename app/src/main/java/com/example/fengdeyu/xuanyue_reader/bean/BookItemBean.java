package com.example.fengdeyu.xuanyue_reader.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengdeyu on 2016/11/23.
 */

public class BookItemBean{
    public String bookIconUrl="";
    public String bookTitle="";
    public String bookAuthor="";
    public String bookContent="";
    public String bookHref="";
    public int currentChapter=0;
    public float rate=0;            //阅读进度
    public String bookIntro;
    public List<ChapterContentBean> mChapterList=new ArrayList<>();


}
