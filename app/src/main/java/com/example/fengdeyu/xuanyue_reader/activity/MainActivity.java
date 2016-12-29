package com.example.fengdeyu.xuanyue_reader.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.adapter.FragmentAdapter;
import com.example.fengdeyu.xuanyue_reader.adapter.ScanBookAdapter;
import com.example.fengdeyu.xuanyue_reader.bean.BookItemBean;
import com.example.fengdeyu.xuanyue_reader.fragment.BookcaseFragment;
import com.example.fengdeyu.xuanyue_reader.fragment.HomeFindFragment;
import com.example.fengdeyu.xuanyue_reader.other.SearchFileTestActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private TextView tv_toLogin;

    private List<BookItemBean> bookItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("轩阅");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.dl_main_drawer);
        NavigationView navigationView= (NavigationView) findViewById(R.id.nv_main_navigation);
        if(navigationView!=null){
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    item.setCheckable(true);
                    mDrawerLayout.closeDrawers();
                    return true;
                }
            });
        }

        View headerDrawerView=navigationView.inflateHeaderView(R.layout.navigation_header);
        tv_toLogin= (TextView) headerDrawerView.findViewById(R.id.tv_toLogin);
        tv_toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                MainActivity.this.finish();
            }
        });



        initViewPager();

    }

    private void initViewPager() {
        mTabLayout= (TabLayout) findViewById(R.id.tabs);
        mViewPager= (ViewPager) findViewById(R.id.viewpager);

        List<String> titles=new ArrayList<>();

        titles.add("书架");
        titles.add("发现");

        for(int i=0;i<titles.size();i++){
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }

        List<Fragment> fragments=new ArrayList<>();

//        loadList();
//
//        BookcaseFragment bookcaseFragment=new BookcaseFragment();
//
//        Bundle bundle=new Bundle();
//
//        bundle.putParcelable("bookcaseList", (Parcelable) bookItemList);
//
//        bookcaseFragment.setArguments(bundle);

        fragments.add(new BookcaseFragment());//加载书架界面



        fragments.add(new HomeFindFragment());//加载发现界面

        FragmentAdapter mFragmentAdapteradapter=
                new FragmentAdapter(getSupportFragmentManager(),fragments,titles);

        mViewPager.setAdapter(mFragmentAdapteradapter);

        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.setTabsFromPagerAdapter(mFragmentAdapteradapter);






    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.ab_search:
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            case R.id.ab_search_file:
                startActivity(new Intent(MainActivity.this, ScanBookActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overaction,menu);
        return true;
    }




}
