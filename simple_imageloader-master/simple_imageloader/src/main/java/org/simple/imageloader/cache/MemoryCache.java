package org.simple.imageloader.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import org.simple.imageloader.request.BitmapRequest;

/**
 * Created by dorian_d on 2017/12/8.
 */

public class MemoryCache implements BitmapCache {

    private LruCache<String,Bitmap> mMemoryCache;

    public MemoryCache(){
        final int maxMemory=Runtime.getRuntime().availableProcessors()+1;
        final int cacheSize=maxMemory/4;
        mMemoryCache=new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

    @Override
    public Bitmap get(BitmapRequest key) {
        return mMemoryCache.get(key.imageUri);
    }

    @Override
    public void put(BitmapRequest key, Bitmap value) {
        mMemoryCache.put(key.imageUri,value);
    }

    @Override
    public void remove(BitmapRequest key) {
        mMemoryCache.remove(key.imageUri);
    }
}
