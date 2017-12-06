package com.example.howard.imageloader.tools.s01;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.LruCache;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dorian_d on 2017/12/6.
 */

public class ImageLoader {
    ImageCache mImageCache=new ImageCache();
    ExecutorService mExecutorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    Handler mUiHandler=new Handler(Looper.getMainLooper());

    public void displayImage(final  String url, final ImageView imageView){
        Bitmap bitmap=mImageCache.get(url);
        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
            return;
        }
        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap=downloadImage(url);
                if (bitmap==null){
                  return;
                }
                if(imageView.getTag().equals(url)){
                  updateImageView(imageView,bitmap);
                }
                mImageCache.put(url,bitmap);
            }
        });
    }

    private void updateImageView(final ImageView imageView,final Bitmap bmp){
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bmp);
            }
        });
    }

    public Bitmap downloadImage(String imageUrl){
        Bitmap bitmap=null;
        try {
           URL url=new  URL(imageUrl);
           final HttpURLConnection conn= (HttpURLConnection)url.openConnection();
           bitmap= BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        }catch(Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }
}
