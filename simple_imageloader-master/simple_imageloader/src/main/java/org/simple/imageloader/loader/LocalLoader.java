package org.simple.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import org.simple.imageloader.request.BitmapRequest;
import org.simple.imageloader.utils.BitmapDecoder;

import java.io.File;

/**
 * Created by dorian_d on 2017/12/8.
 */

public class LocalLoader extends AbsLoader {
    @Override
    protected Bitmap onLoadImage(BitmapRequest request) {
        final String imagePath= Uri.parse(request.imageUri).getPath();
        final File imgFile=new File(imagePath);
        if(!imgFile.exists()){
            return null;
        }
        request.justCacheInMem=true;
        BitmapDecoder decoder=new BitmapDecoder() {
            @Override
            public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(imagePath,options);
            }
        };

        return decoder.decodeBitmap(request.getImageViewWidth(),request.getImageViewHeight());
    }
}
