package com.example.fengdeyu.xuanyue_reader.other;

import android.graphics.Color;

/**
 * Created by fengdeyu on 2017/2/7.
 */

public class GetPageAttribute {

    /**
     * 单例模式
     */
    private static GetPageAttribute getPageAttribute=null;
    public static GetPageAttribute getInstance(){
        if (getPageAttribute==null){
            getPageAttribute=new GetPageAttribute();
        }
        return getPageAttribute;
    }


    /**
     * 缓存相关参数
     */
    public boolean isDownloading=false; //是否在下载
    public int downloadedNum=0;         //已经下载了多少章
    public int downloadNum=1;           //需要下载多少章

    /**
     * 文本内容相关
     */
    public String contents;             //文本内容
    public String chapterName;          //章节名字
    public int textColor=0xff444444;    //字体颜色
    public int textSize=20;             //字体大小
    public String font_typeface="FZXH.TTF";        //字体
    public int linespace=20;                       //行间距

    public float rate=0;                //当前页面

    public int bg_theme= 0xffc9c9c9;             //阅读背景

    public String day_night="day";              //日夜间flag


    /**
     * 手机相关状态
     */
    public int battery;                 //手机电量
    public int brightness;              //系统亮度




}
