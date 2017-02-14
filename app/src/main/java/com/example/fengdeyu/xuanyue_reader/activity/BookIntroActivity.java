package com.example.fengdeyu.xuanyue_reader.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fengdeyu.xuanyue_reader.R;
import com.example.fengdeyu.xuanyue_reader.bean.BookItemBean;
import com.example.fengdeyu.xuanyue_reader.other.GetBookCase;
import com.example.fengdeyu.xuanyue_reader.other.GetChapterContent;
import com.example.fengdeyu.xuanyue_reader.other.ImageLoader;

public class BookIntroActivity extends AppCompatActivity {

    private ImageView iv_back;
    private Button btn_start_read;
    private Button btn_add_book;

    private TextView bookTitle;
    private TextView bookAuthor;
    private TextView bookContent;
    private TextView bookIntro;
    private ImageView bookIcon;

    private String bookHref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_intro);


        iv_back= (ImageView) findViewById(R.id.iv_back);
        btn_start_read= (Button) findViewById(R.id.btn_start_read);
        btn_add_book= (Button) findViewById(R.id.btn_add_book);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        init();

        Intent intent=getIntent();
        bookTitle.setText(intent.getStringExtra("bookTitle"));
        bookAuthor.setText(intent.getStringExtra("bookAuthor"));
        bookContent.setText(intent.getStringExtra("bookContent"));
        bookIntro.setText(intent.getStringExtra("bookIntro"));
        new ImageLoader().showImageByThread(bookIcon,intent.getStringExtra("bookIconUrl"));

        bookHref=intent.getStringExtra("bookHref");




    }

    @Override
    protected void onResume() {
        super.onResume();
        new loadChapterContentAsyncTask().execute(bookHref);
    }

    public void init(){
        bookTitle= (TextView) findViewById(R.id.tv_book_title);
        bookAuthor= (TextView) findViewById(R.id.tv_book_author);
        bookContent= (TextView) findViewById(R.id.tv_book_content);
        bookIntro= (TextView) findViewById(R.id.tv_book_intro);
        bookIcon= (ImageView) findViewById(R.id.iv_book_icon);
    }

    public class loadChapterContentAsyncTask extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... params) {
            GetChapterContent.getInstance().loadChapterContent(params[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            btn_start_read.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(BookIntroActivity.this,ReadOnlineBookActivity.class);
                    GetChapterContent.getInstance().currentChapter=0;
                    GetChapterContent.getInstance().bookTitle=getIntent().getStringExtra("bookTitle");
                    startActivity(intent);
                }
            });
            btn_add_book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final BookItemBean bookItemBean=new BookItemBean();
                    bookItemBean.bookTitle=getIntent().getStringExtra("bookTitle");
                    bookItemBean.bookAuthor=getIntent().getStringExtra("bookAuthor");
                    bookItemBean.bookContent=getIntent().getStringExtra("bookContent");
                    bookItemBean.bookIconUrl=getIntent().getStringExtra("bookIconUrl");
                    bookItemBean.bookHref=getIntent().getStringExtra("bookHref");
//                    for(int i=0;i<GetChapterContent.getInstance().mList.size();i++){
//                        bookItemBean.mChapterList.add(GetChapterContent.getInstance().mList.get(i));
//                    }

                    if(GetBookCase.getInstance().hasBook(bookItemBean)){
                        Toast.makeText(BookIntroActivity.this,"此书已在书架,请勿重复添加!",Toast.LENGTH_SHORT).show();
                    }else {
                        GetBookCase.getInstance().mList.add(bookItemBean);
                        new Thread(){
                            @Override
                            public void run() {
                                super.run();
                                GetBookCase.getInstance().loadChapterContent(GetBookCase.getInstance().mList.size()-1,bookItemBean.bookHref);
                            }
                        }.start();

                        Toast.makeText(BookIntroActivity.this,"加入书架成功!",Toast.LENGTH_SHORT).show();

                        }




                }
            });

        }
    }

}
