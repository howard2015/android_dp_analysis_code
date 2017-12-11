package org.simple.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.disklrucache.IOUtil;

import org.simple.imageloader.request.BitmapRequest;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dorian_d on 2017/12/8.
 */

public class UrlLoader extends AbsLoader {
    @Override
    protected Bitmap onLoadImage(BitmapRequest request) {
        final String imageUrl = request.imageUri;
        FileOutputStream fos = null;
        InputStream is = null;
        Bitmap bitmap = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(imageUrl);
            conn = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(conn.getInputStream());
            bitmap = BitmapFactory.decodeStream(is, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeQuietly(is);
            IOUtil.closeQuietly(fos);
            if (conn != null) {
                // 关闭流
                conn.disconnect();
            }
        }
        return bitmap;
    }
}
