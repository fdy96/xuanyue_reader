package com.example.fengdeyu.xuanyue_reader.other;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fengdeyu.xuanyue_reader.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DownloadTestActivity extends AppCompatActivity {

    String URL="http://www.23us.so/files/article/html/10/10639/index.html";


    private Button btn_get;
    private Button btn_download;
    private Button btn_read;
    private TextView textView;

    public int num=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_test);

        btn_get= (Button) findViewById(R.id.get);
        btn_download= (Button) findViewById(R.id.download);
        btn_read= (Button) findViewById(R.id.read);
        textView= (TextView) findViewById(R.id.textView);

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GetChapterContent.getInstance().mList.size()==0){
                    new loadChapterContentAsyncTask().execute(URL);
                }else{
                    Toast.makeText(DownloadTestActivity.this,"成功   "+GetChapterContent.getInstance().mList.size(),Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dirPath=getFilesDir()+"/10639/";

                File dir=new File(dirPath);
                if(!dir.exists()){
                    dir.mkdirs();
                }
                new downloadAsyncTask().execute(dirPath);
            }
        });

        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                read();

            }
        });

    }

    private void read() {
        try {
            FileInputStream inputStream=new FileInputStream(getFilesDir()+"/10639/"+"3541882.txt");
            byte[] bytes = new byte[1024];
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            while (inputStream.read(bytes) != -1) {
                arrayOutputStream.write(bytes, 0, bytes.length);
            }
            inputStream.close();
            arrayOutputStream.close();
            String contents = new String(arrayOutputStream.toByteArray());
            textView.setText(contents);
            Log.i("info",contents+"123");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public class loadChapterContentAsyncTask extends AsyncTask<String,Void,Void> {

        @Override
        protected Void doInBackground(String... params) {
            GetChapterContent.getInstance().loadChapterContent(params[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(DownloadTestActivity.this,"成功   "+GetChapterContent.getInstance().mList.size(),Toast.LENGTH_SHORT).show();
        }
    }
    public class downloadAsyncTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            for(int j=0;j<GetChapterContent.getInstance().mList.size();j++){
                String s=GetChapterContent.getInstance().mList.get(j).chapter_url;
                String fileName=s.substring(s.lastIndexOf("/")+1,s.lastIndexOf("."));

                File file=new File(params[0]+fileName+".txt");
                if(!file.exists()){
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Document doc = null;
                String contents="";
                try {
                    doc = Jsoup.connect(s).get();
                    String str_contents = doc.getElementById("contents").text();
                    StringBuilder sb = new StringBuilder(str_contents);
                    int i = 0;
                    while (sb.indexOf(" ", i + 3) != -1) {
                        i = sb.indexOf(" ", i + 3);
                        sb.insert(i, "\n");
                    }
                    contents=sb.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                FileOutputStream outputStream;
                try {
                    outputStream=new FileOutputStream(params[0]+fileName+".txt");
                    outputStream.write(contents.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                num++;
                Log.i("info",num+"");


            }



            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Toast.makeText(DownloadTestActivity.this,"下载完成",Toast.LENGTH_SHORT).show();
        }
    }



    public class loadContentsAsyncTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                Document doc = Jsoup.connect(params[0]).get();
                String contents = doc.getElementById("contents").text();

                StringBuilder sb = new StringBuilder(contents);
                int i = 0;
                while (sb.indexOf(" ", i + 3) != -1) {
                    i = sb.indexOf(" ", i + 3);
                    sb.insert(i, "\n");
                }

                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String contents) {
            super.onPostExecute(contents);

            Log.i("info", contents);

        }
    }
}
