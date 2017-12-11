package org.simple.imageloader.cache;

import android.graphics.Bitmap;

import org.simple.imageloader.request.BitmapRequest;

/**
 * Created by dorian_d on 2017/12/8.
 */

public interface BitmapCache {
    public Bitmap get(BitmapRequest key);

    public void put(BitmapRequest key, Bitmap value);

    public void remove(BitmapRequest key);
}
