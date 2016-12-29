package com.example.fengdeyu.xuanyue_reader.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.adapter.ScanBookAdapter;
import com.example.fengdeyu.xuanyue_reader.bean.ScanBookBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScanBookActivity extends AppCompatActivity {
    private RecyclerView rv_scan_book;
    private List<ScanBookBean> mList;

    private ImageView iv_back;
    private TextView tv_select_all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_book);

        rv_scan_book= (RecyclerView) findViewById(R.id.rv_scan_book);

        mList=new ArrayList<>();

        new ScanBookAsyncTask().execute();

        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_select_all= (TextView) findViewById(R.id.tv_select_all);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mList.clear();
    }

    public class ScanBookAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {

            File file =new File("/storage/emulated/0/");


            search(file);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            final ScanBookAdapter adapter=new ScanBookAdapter(ScanBookActivity.this,mList);

            adapter.setOnItemClickListener(new ScanBookAdapter.onItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ImageView iv_select=(ImageView)view.findViewById(R.id.iv_select);
                    if(mList.get(position).isSelect==false) {
                        mList.get(position).isSelect=true;
                        iv_select.setImageResource(R.mipmap.ic_checkbox_selected);
                    }else{
                        mList.get(position).isSelect=false;
                        iv_select.setImageResource(R.mipmap.ic_checkbox_normal);
                    }
                }
            });

            rv_scan_book.setLayoutManager(new LinearLayoutManager(rv_scan_book.getContext()));

            rv_scan_book.setAdapter(adapter);

            tv_select_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i=0;i<mList.size();i++){
                        if(mList.get(i).isSelect==false) {
                            mList.get(i).isSelect=true;
                        }
                    }
                    adapter.notifyDataSetChanged();

                }
            });




        }
    }


    public void search(File fileOld){
        FileInputStream fis;

        if(!fileOld.getPath().equals("/storage/emulated/0/Android/data")) {
            File[] files = fileOld.listFiles();
            if (files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    if (!files[i].isDirectory()) {

                        if (files[i].getName().indexOf(".txt") > -1) {

                            try {
                                fis=new FileInputStream(files[i]);
                                if(fis.available()>2048){
                                    ScanBookBean scanBookBean=new ScanBookBean();
                                    scanBookBean.fileName=files[i].getName();
                                    float size;


                                    if(fis.available()>1024*1024){
                                        size=fis.available()/(1024.0f*1024.0f);
                                        size=(int)(size*100)/100.0f;
                                        scanBookBean.fileSize=size+"MB";
                                    }
                                    else if(fis.available()>1024){
                                        size=fis.available()/1024.0f;
                                        size=(int)(size*100)/100.0f;
                                        scanBookBean.fileSize=size+"KB";
                                    }
                                    else {
                                        size=fis.available();
                                        size=(int)(size*100)/100.0f;
                                        scanBookBean.fileSize=size+"B";
                                    }



                                    mList.add(scanBookBean);

                                    Log.i("info", files[i].getPath());

                                }
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }



                        }
                    } else {
                        search(files[i]);
                    }
                }
            }
        }


    }

}
