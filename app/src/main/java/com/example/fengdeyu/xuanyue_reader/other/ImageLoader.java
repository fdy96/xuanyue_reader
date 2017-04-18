package com.example.fengdeyu.xuanyue_reader.other;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
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

    private static ImageLoader imageLoader=null;
    public static ImageLoader getInstance(){
        if(imageLoader==null){
            imageLoader=new ImageLoader();
        }

        return imageLoader;

    }




    private ImageView mImageView;

    private LruCache<String,Bitmap> mCache;
    public ImageLoader(){
        int cacheSize=(int)(Runtime.getRuntime().maxMemory())/4;
        mCache=new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        Log.i("info",mCache.size()+"");
    }

    private void addBitmapToCache(String url,Bitmap bitmap){
        if(getBitmapFromCache(url)==null){
            mCache.put(url,bitmap);
            Log.i("info","put");
            Log.i("info",mCache.size()+"");
        }
    }
    private Bitmap getBitmapFromCache(String url){
        return mCache.get(url);
    }


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mImageView.setImageBitmap((Bitmap) msg.obj);

        }
    };


    public void showImageByThread(final ImageView imageView, final String url){
        mImageView=imageView;

        Bitmap bitmap=getBitmapFromCache(url);
        if(bitmap==null) {
            Log.i("info","byUrl");

            new Thread() {
                @Override
                public void run() {
                    super.run();
                    Bitmap bitmap = getBitmapFromURL(url);
                    if(bitmap!=null) {
                        addBitmapToCache(url, bitmap);
                    }
                    Message message = Message.obtain();
                    message.obj = bitmap;
                    handler.sendMessage(message);

                }
            }.start();
        }else {
            Log.i("info","byCache");
            mImageView.setImageBitmap(bitmap);
        }
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
            is.close();
            return bitmap;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
