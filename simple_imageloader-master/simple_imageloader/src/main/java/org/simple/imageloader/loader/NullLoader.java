package org.simple.imageloader.loader;

import android.graphics.Bitmap;
import android.util.Log;

import org.simple.imageloader.request.BitmapRequest;

/**
 * Created by dorian_d on 2017/12/8.
 */

public class NullLoader extends AbsLoader{
    @Override
    protected Bitmap onLoadImage(BitmapRequest request) {
        Log.e(NullLoader.class.getSimpleName(), "### wrong schema, your image uri is : "
                + request.imageUri);
        return null;
    }
}
