package com.example.fengdeyu.xuanyue_reader.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.adapter.GuideViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 开始引导页，第一次安装时打开，负责介绍app的特点作用
 */
public class GuideViewActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private List<View> mViews;      //引导页的N个布局页面
    private ViewPager vp_guide;     //存放页面的viewpager

    private ImageView[] dots=new ImageView[3];     //三个点
    private int[] ids={R.id.iv_dot1,R.id.iv_dot2,R.id.iv_dot3};     //三个点的id

    private Button btn_start;       //跳转按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_view);

        vp_guide= (ViewPager) findViewById(R.id.vp_guide);
        for(int i=0;i<3;i++){
            dots[i]= (ImageView) findViewById(ids[i]);
        }



        LayoutInflater layoutInflater=LayoutInflater.from(this);
        mViews=new ArrayList<>();
        mViews.add(layoutInflater.inflate(R.layout.one,null));
        mViews.add(layoutInflater.inflate(R.layout.two,null));
        mViews.add(layoutInflater.inflate(R.layout.three,null));
        //添加布局视图

        btn_start= (Button) mViews.get(2).findViewById(R.id.btn_start);
        //初始化控件

        GuideViewPagerAdapter guideViewPagerAdapter=new GuideViewPagerAdapter(mViews,this);

        vp_guide.setAdapter(guideViewPagerAdapter);         //添加适配器

        vp_guide.setOnPageChangeListener(this);             //监听事件

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideViewActivity.this,MainActivity.class));  //跳转到主界面
                finish();
            }
        });

    }

    /**
     * 滚动之后，改变点的图片
     * @param position  当前选择布局
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        for(int i=0;i<3;i++){
            if(i==position){
                this.dots[i].setImageResource(R.mipmap.ic_dot_selected);
            }else{
                this.dots[i].setImageResource(R.mipmap.ic_dot_default);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
