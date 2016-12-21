package com.example.fengdeyu.xuanyue_reader.bean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by fengdeyu on 2016/12/13.
 */

public class ChapterContentBean {

    public String chapter_name;
    public String chapter_url;


    public boolean isDownload=false;
    public String chapter_local_url=null;
}
