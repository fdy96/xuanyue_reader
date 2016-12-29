package com.example.fengdeyu.xuanyue_reader.other;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fengdeyu.xuanyue_reader.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchFileTestActivity extends AppCompatActivity {

    private EditText et_file_dir;
    private Button btn_search;
    private TextView tv_result;

    private String key="";
    private String path;

    private boolean isClick=false;



    private File file;

    private FileInputStream fis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_file_test);

        et_file_dir= (EditText) findViewById(R.id.et_file_dir);
        btn_search= (Button) findViewById(R.id.btn_search);
        tv_result= (TextView) findViewById(R.id.tv_result);

        file=new File("/storage/emulated/0/");

//        file=getFilesDir();
//
//        File[] files=file.listFiles();
//
//
//

//        Log.i("info",file.getPath());
//        Log.i("info",files.length+"");
//
//        for(int i=0;i<10;i++){
//            Log.i("info",files[i].getPath());
//            Log.i("info",files[i].exists()+"");
//        }
//        Log.i("info",files[0].getPath());
//        Log.i("info",files[0].exists()+"");

        //queryFiles();

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!key.equals(et_file_dir.getText().toString().trim())){
                    path="";
                    key=et_file_dir.getText().toString().trim();
                    tv_result.setText("");
                    browserFile();
                }

            }
        });

    }
    public void browserFile() {
        if (key.equals("")) {
            Toast.makeText(this, "请输入查找内容", Toast.LENGTH_SHORT).show();
        } else {
            new searchFileAsyncTask().execute();



        }
    }


    public class searchFileAsyncTask extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... params) {


            search(file);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            tv_result.setText(path);
            Log.i("asdffghfgj","asdsgbdigj");
            if (tv_result.getText().equals("")) {
                Toast.makeText(SearchFileTestActivity.this, "未搜索到相应文件", Toast.LENGTH_SHORT).show();
            }

        }
    }



    public void search(File fileOld){

        if(!fileOld.getPath().equals("/storage/emulated/0/Android/data")) {
            File[] files = fileOld.listFiles();
            if (files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    if (!files[i].isDirectory()) {

                        if (files[i].getName().indexOf(key) > -1) {

                            try {
                                fis=new FileInputStream(files[i]);
                                if(fis.available()>2048){
                                    path += "\n" + files[i].getName();
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
