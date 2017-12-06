package com.example.howard.imageloader.tools.s02;

import android.graphics.Bitmap;

/**
 * Created by dorian_d on 2017/12/6.
 */

public class DoubleCache {
    ImageCache mMemoryCache= new ImageCache();
    DiskCache mDiskCache=new DiskCache();
    public Bitmap get(String url){
        Bitmap bitmap=mMemoryCache.get(url);
            if(bitmap==null){
                mDiskCache.get(url);
            }
         return bitmap;
    }
    public void put(String url,Bitmap bitmap){
        mMemoryCache.put(url,bitmap);
        mDiskCache.put(url,bitmap);
    }
}
