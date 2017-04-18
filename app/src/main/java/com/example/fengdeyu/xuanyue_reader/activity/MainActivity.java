package com.example.fengdeyu.xuanyue_reader.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.adapter.FragmentAdapter;
import com.example.fengdeyu.xuanyue_reader.bean.BookItemBean;
import com.example.fengdeyu.xuanyue_reader.fragment.BookcaseFragment;
import com.example.fengdeyu.xuanyue_reader.fragment.HomeFindFragment;
import com.example.fengdeyu.xuanyue_reader.other.DateCleanServer;
import com.example.fengdeyu.xuanyue_reader.other.GetBookCase;
import com.example.fengdeyu.xuanyue_reader.other.GetPageAttribute;
import com.example.fengdeyu.xuanyue_reader.other.GetUserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private TextView tv_toLogin;
    private ImageView iv_sign;

    private List<BookItemBean> bookItemList;

    BookcaseFragment bookcaseFragment;
    HomeFindFragment homeFindFragment;

    List<Fragment> fragments;
    FragmentAdapter mFragmentAdapteradapter;
    List<String> titles;


    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            switch (msg.what){
                case 0:
                    mFragmentAdapteradapter.notifyDataSetChanged();
                    break;
                case 1:
                    Toast.makeText(MainActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(MainActivity.this,"服务器连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(MainActivity.this,"同步完成",Toast.LENGTH_SHORT).show();
                    break;
            }




        }
    };

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
                    item.setCheckable(false);
                    mDrawerLayout.closeDrawers();

                    switch (item.getItemId()){
                        case R.id.item_date_clean:
                            String cacheSize="当前缓存为：";
                            try {
                                cacheSize+=DateCleanServer.getTotalCacheSize(MainActivity.this)+"，";
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                            dialog.setMessage(cacheSize+"是否清除缓存？");
                            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    new Thread(){
                                        @Override
                                        public void run() {
                                            super.run();
                                            DateCleanServer.clearAllCache(MainActivity.this);

                                        }
                                    }.start();

                                }
                            });
                            dialog.setNegativeButton("取消",null);

                            dialog.show();

                            break;

//                        case R.id.item_setting:
//                            startActivity(new Intent(MainActivity.this,SettingActivity.class));
//                            break;
                        case R.id.item_scan_book:
                            startActivity(new Intent(MainActivity.this, ScanBookActivity.class));
                            break;
                        case R.id.item_feedback:
                            startActivity(new Intent(MainActivity.this,FeedbackActivity.class));
                            break;
                        case R.id.item_upload:
                            if(GetUserInfo.getInstance().sign) {
                                new Thread() {
                                    @Override
                                    public void run() {
                                        super.run();
                                        String url = "http://10.0.2.2:8080/XuanyueReaderServer/UpdateBookServlet?uid=" + GetUserInfo.getInstance().userInfo.uid;
                                        Connection conn = Jsoup.connect(url);

                                        Map<String, Object> book = new HashMap<String, Object>();
                                        List<String> bookArray = new ArrayList<String>();
                                        for (int i = 0; i < GetBookCase.getInstance().mList.size(); i++) {
                                            if (!GetBookCase.getInstance().mList.get(i).bookAuthor.equals("本地")) {
                                                bookArray.add(GetBookCase.getInstance().mList.get(i).bookTitle);
                                            }
                                        }

                                        book.put("book", bookArray);

                                        conn.data("bookcase", new JSONObject(book).toString());
                                        try {
                                            Document doc = conn.post();


                                            if(doc.text().equals("true")){
                                                handler.sendEmptyMessageDelayed(1, 10);
                                            }else {
                                                handler.sendEmptyMessageDelayed(2, 10);
                                            }

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }




                                    }
                                }.start();
                            }else {
                                Toast.makeText(MainActivity.this,"当前未登录",Toast.LENGTH_SHORT).show();
                            }

                            break;
                    }


                    return true;
                }
            });
        }

        View headerDrawerView=navigationView.inflateHeaderView(R.layout.navigation_header);
        headerDrawerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GetUserInfo.getInstance().sign){
                    startActivity(new Intent(MainActivity.this,PersonalCenterActivity.class));
                }else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });


        tv_toLogin= (TextView) headerDrawerView.findViewById(R.id.tv_toLogin);
//        tv_toLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(GetUserInfo.getInstance().sign){
//                    startActivity(new Intent(MainActivity.this,PersonalCenterActivity.class));
//                }else {
//                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                    finish();
//                }
//            }
//        });
        iv_sign= (ImageView) headerDrawerView.findViewById(R.id.iv_sign);



        initViewPager();

        if(!GetPageAttribute.getInstance().isNetworkConnected(this)){
            Toast.makeText(MainActivity.this,"当前无网络连接", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        tv_toLogin.setText("请登录");
        iv_sign.setBackgroundResource(R.mipmap.ic_launcher);
        if(GetUserInfo.getInstance().sign){
            tv_toLogin.setText(GetUserInfo.getInstance().userInfo.nickname);
            Log.i("head",GetUserInfo.getInstance().userInfo.head_portrait);

            switch (GetUserInfo.getInstance().userInfo.head_portrait){
                case "0":
                    iv_sign.setBackgroundResource(R.mipmap.ic_launcher);
                    break;
                case "1":
                    iv_sign.setBackgroundResource(R.mipmap.head_portrait_1);
                    break;
                case "2":
                    iv_sign.setBackgroundResource(R.mipmap.head_portrait_2);
                    break;
                case "3":
                    iv_sign.setBackgroundResource(R.mipmap.head_portrait_3);
                    break;

            }

        }
    }

    private void initViewPager() {
        mTabLayout= (TabLayout) findViewById(R.id.tabs);
        mViewPager= (ViewPager) findViewById(R.id.viewpager);

        titles=new ArrayList<>();

        titles.add("书架");
        titles.add("发现");

        for(int i=0;i<titles.size();i++){
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }

        fragments=new ArrayList<>();

//        bookcaseFragment=new BookcaseFragment();
//        homeFindFragment=new HomeFindFragment();




        fragments.add(new BookcaseFragment());//加载书架界面

        fragments.add(new HomeFindFragment());//加载发现界面

        mFragmentAdapteradapter=
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
                break;
            case R.id.ab_sync_book:
                Log.i("click","sync_book");
                if(GetUserInfo.getInstance().sign) {
                    if(GetPageAttribute.getInstance().isNetworkConnected(MainActivity.this)) {

                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                String url = "http://10.0.2.2:8080/XuanyueReaderServer/LoadBookServlet?uid=" + GetUserInfo.getInstance().userInfo.uid;
                                try {
                                    Document doc = Jsoup.connect(url).get();
                                    JSONObject bookObj = new JSONObject(doc.text());
                                    JSONArray bookArray = bookObj.getJSONArray("bookcase");
                                    for (int i = 0; i < bookArray.length(); i++) {
                                        Log.i("info", bookArray.get(i).toString());

                                        BookItemBean bookItemBean = GetBookCase.getInstance().addBookByTitle(bookArray.get(i).toString());
                                        if (GetBookCase.getInstance().addBook(bookItemBean)) {

                                            GetBookCase.getInstance().loadChapterContent(GetBookCase.getInstance().mList.size() - 1, bookItemBean.bookHref);


                                        }
                                        handler.sendEmptyMessageDelayed(0, 10);
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                handler.sendEmptyMessageDelayed(3, 10);


                            }
                        }.start();
                    }else {
                        Toast.makeText(MainActivity.this,"服务器连接失败",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(MainActivity.this,"当前未登录",Toast.LENGTH_SHORT).show();
                }


                break;
//            case R.id.ab_search_file:
//                startActivity(new Intent(MainActivity.this, ScanBookActivity.class));
//                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_overaction,menu);
        return true;
    }




}
