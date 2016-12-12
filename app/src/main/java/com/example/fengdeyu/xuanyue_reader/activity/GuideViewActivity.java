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

public class GuideViewActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private List<View> mViews;
    private GuideViewPagerAdapter guideViewPagerAdapter;
    private ViewPager vp_guide;

    private ImageView[] dots=new ImageView[3];
    private int[] ids={R.id.iv_dot1,R.id.iv_dot2,R.id.iv_dot3};

    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_view);

        LayoutInflater layoutInflater=LayoutInflater.from(this);
        mViews=new ArrayList<>();
        mViews.add(layoutInflater.inflate(R.layout.one,null));
        mViews.add(layoutInflater.inflate(R.layout.two,null));
        mViews.add(layoutInflater.inflate(R.layout.three,null));

        guideViewPagerAdapter=new GuideViewPagerAdapter(mViews,this);
        vp_guide= (ViewPager) findViewById(R.id.vp_guide);
        vp_guide.setAdapter(guideViewPagerAdapter);
        vp_guide.setOnPageChangeListener(this);

        for(int i=0;i<3;i++){
            dots[i]= (ImageView) findViewById(ids[i]);
        }

        btn_start= (Button) mViews.get(2).findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideViewActivity.this,MainActivity.class));
                finish();
            }
        });

    }

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
