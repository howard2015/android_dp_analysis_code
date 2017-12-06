package com.example.howard.imageloader.tools.s02;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
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
    DiskCache mDiskCache=new DiskCache();
    DoubleCache mDoubleCache=new DoubleCache();
    boolean isUseDiskCache=false;
    boolean isUseDoubleCache=false;
    ExecutorService mExecutorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    Handler mUiHandler=new Handler(Looper.getMainLooper());

    public void displayImage(final  String url, final ImageView imageView){
        Bitmap bitmap=null;
        if(isUseDoubleCache){
           mDoubleCache.get(url);
        }else if(isUseDiskCache){
            mDiskCache.get(url);
        }else{
            mImageCache.get(url);
        }
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
                if(isUseDoubleCache()){
                   mDoubleCache.put(url,bitmap);
                }else if(isUseDiskCache()){
                    mDiskCache.put(url,bitmap);
                }else{
                    mImageCache.put(url,bitmap);
                }
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

    public boolean isUseDiskCache() {
        return isUseDiskCache;
    }

    public void useDiskCache(boolean useDiskCache) {
        isUseDiskCache = useDiskCache;
    }

    public boolean isUseDoubleCache() {
        return isUseDoubleCache;
    }

    public void useDoubleCache(boolean useDoubleCache) {
        isUseDoubleCache = useDoubleCache;
    }
}
