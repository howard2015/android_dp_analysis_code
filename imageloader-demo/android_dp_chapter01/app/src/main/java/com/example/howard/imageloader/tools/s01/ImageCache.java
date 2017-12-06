package com.example.howard.imageloader.tools.s01;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by dorian_d on 2017/12/6.
 */

public class ImageCache {
    LruCache<String,Bitmap> mImageCache;
    public ImageCache(){
        initImageCache();
    }
    private void initImageCache(){
        final int maxMemory = (int)(Runtime.getRuntime().maxMemory() /1024);
        final  int cacheSize = maxMemory/4;
        mImageCache=new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {

                return bitmap.getRowBytes()*bitmap.getHeight() / 1024;
            }
        };
    }
    public void put(String url,Bitmap bitmap){
        mImageCache.put(url,bitmap) ;
    }

    public Bitmap get(String url){
        return mImageCache.get(url);
    }
}
