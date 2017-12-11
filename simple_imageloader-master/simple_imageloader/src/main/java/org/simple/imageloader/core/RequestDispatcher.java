package org.simple.imageloader.core;

import android.util.Log;

import org.simple.imageloader.loader.Loader;
import org.simple.imageloader.loader.LoaderManager;
import org.simple.imageloader.request.BitmapRequest;

import java.util.concurrent.BlockingQueue;

/**
 * Created by dorian_d on 2017/12/8.
 */

public class RequestDispatcher extends Thread {
    /**
     * 网络请求队列
     */
    private BlockingQueue<BitmapRequest> mRequestQueue;

    /**
     * @param queue 图片加载请求队列
     */
    public RequestDispatcher(BlockingQueue<BitmapRequest> queue) {
        mRequestQueue = queue;
    }

    @Override
    public void run() {
        try {
            while (!this.isInterrupted()) {
                final BitmapRequest request = mRequestQueue.take();
                if (request.isCancel) {
                    continue;
                }
                // 解析图片schema
                final String schema = parseSchema(request.imageUri);
                // 根据schema获取对应的Loader
                Loader imageLoader = LoaderManager.getInstance().getLoader(schema);
                // 加载图片
                imageLoader.loadImage(request);
            }
        } catch (InterruptedException e) {
            Log.i("", "### 请求分发器退出");
        }
    }

    /**
     * 这里是解析图片uri的格式,uri格式为: schema:// + 图片路径。
     */
    private String parseSchema(String uri) {
        if (uri.contains("://")) {
            return uri.split("://")[0];
        } else {
            Log.e(getName(), "### wrong scheme, image uri is : " + uri);
        }


        return "";
    }
}
