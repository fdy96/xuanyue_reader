package com.example.fengdeyu.xuanyue_reader.other;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.example.fengdeyu.xuanyue_reader.R;

import org.jsoup.helper.HttpConnection;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by fengdeyu on 2016/12/11.
 */

public class ImageLoader {

    private ImageView mImageView;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mImageView.setImageBitmap((Bitmap) msg.obj);

        }
    };


    public void showImageByThread(final ImageView imageView, final String url){
        mImageView=imageView;

        new Thread(){
            @Override
            public void run() {
                super.run();
                Bitmap bitmap=getBitmapFromURL(url);
                Message message=Message.obtain();
                message.obj=bitmap;
                handler.sendMessage(message);


            }
        }.start();
    }

    public Bitmap getBitmapFromURL(String urlString){
        Bitmap bitmap;
        InputStream is=null;

        try {
            URL url=new URL(urlString);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            is=new BufferedInputStream(conn.getInputStream());
            bitmap= BitmapFactory.decodeStream(is);
            conn.disconnect();
            return bitmap;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return null;
    }
}
