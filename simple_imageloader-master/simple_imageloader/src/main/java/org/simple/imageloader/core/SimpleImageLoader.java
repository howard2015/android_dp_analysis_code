package org.simple.imageloader.core;

import android.graphics.Bitmap;
import android.widget.ImageView;

import org.simple.imageloader.cache.BitmapCache;
import org.simple.imageloader.cache.MemoryCache;
import org.simple.imageloader.cache.NoCache;
import org.simple.imageloader.config.DisplayConfig;
import org.simple.imageloader.config.ImageLoaderConfig;
import org.simple.imageloader.policy.SerialPolicy;
import org.simple.imageloader.request.BitmapRequest;


/**
 * Created by dorian_d on 2017/12/8.
 */

public class SimpleImageLoader {
    /** SimpleImageLoader实例 */
    private static SimpleImageLoader sInstance;
    /** 网络请求队列  */
    private RequestQueue mImageQueue;
    /** 缓存 */
    private volatile BitmapCache mCache = new MemoryCache();
    /** 图片加载配置对象 */
    private ImageLoaderConfig mConfig;

    private SimpleImageLoader(){

    }

    public static SimpleImageLoader getInstance(){
        if(sInstance == null) {
            synchronized (SimpleImageLoader.class){
                if (sInstance == null) {
                    sInstance = new SimpleImageLoader();
                }
            }
        }
        return sInstance;
    }

    /**
     * 初始化ImageLoader，启动请求队列
     * @param config 配置对象
     */
    public void init(ImageLoaderConfig config) {
        mConfig = config;
        mCache = mConfig.bitmapCache;
        checkConfig();
        mImageQueue = new RequestQueue(mConfig.threadCount);
        mImageQueue.start();
    }

    private void checkConfig() {
        if (mConfig == null) {
            throw new RuntimeException(
                    "The config of SimpleImageLoader is Null, please call the init(ImageLoaderConfig config) method to initialize");
        }


        if (mConfig.loadPolicy == null) {
            mConfig.loadPolicy = new SerialPolicy();
        }
        if (mCache == null) {
            mCache = new NoCache();
        }

    }

    public void displayImage(ImageView imageView, String uri) {
        displayImage(imageView, uri, null, null);
    }

    public void displayImage(final ImageView imageView, final String uri,
                             final DisplayConfig config, final ImageListener listener) {
        BitmapRequest request = new BitmapRequest(imageView, uri, config, listener);
        // 加载的配置对象,如果没有设置则使用ImageLoader的配置
        request.displayConfig = request.displayConfig != null ? request.displayConfig
                : mConfig.displayConfig;
        // 添加对队列中
        mImageQueue.addRequest(request);
    }

    public ImageLoaderConfig getConfig() {
        return mConfig;
    }

    public void stop() {
        mImageQueue.stop();
    }
    /**
     * 图片加载Listener
     *
     * @author mrsimple
     */
    public static interface ImageListener {
        public void onComplete(ImageView imageView, Bitmap bitmap, String uri);
    }
}
