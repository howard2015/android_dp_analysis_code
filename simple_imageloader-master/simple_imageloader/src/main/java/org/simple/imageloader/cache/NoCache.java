package org.simple.imageloader.cache;

import android.graphics.Bitmap;

import org.simple.imageloader.request.BitmapRequest;

/**
 * Created by dorian_d on 2017/12/8.
 */

public class NoCache implements BitmapCache {
    @Override
    public Bitmap get(BitmapRequest key) {
        return null;
    }

    @Override
    public void put(BitmapRequest key, Bitmap value) {

    }

    @Override
    public void remove(BitmapRequest key) {

    }
}
