package com.example.fengdeyu.xuanyue_reader.adapter;

import android.view.View;

/**
 * Created by fengdeyu on 2017/1/7.
 */

public abstract class PageAdapter
{
    /**
     * @return 页面view
     */
    public abstract View getView();

    public abstract int getCount();

    public abstract int getCurrentPage();

    /**
     * 将内容添加到view中
     *
     * @param view
     *            包含内容的view
     * @param position
     *            第position页
     */
    public abstract void addContent(View view, int position);
}