package org.simple.imageloader.config;

import org.simple.imageloader.cache.BitmapCache;
import org.simple.imageloader.cache.MemoryCache;
import org.simple.imageloader.policy.LoadPolicy;
import org.simple.imageloader.policy.SerialPolicy;

/**
 * ImageLoader 配置类
 * Created by dorian_d on 2017/12/8.
 */

public class ImageLoaderConfig {
    /**
     * 图片缓存配置对象
     */
   public BitmapCache bitmapCache=new MemoryCache();

    /**
     * 加载图片时的loading和加载失败的图片配置对象
     */
    public DisplayConfig displayConfig =new DisplayConfig();

    /**
     * 加载策略
     */
    public LoadPolicy loadPolicy =new SerialPolicy();

    /**
     * 线程数
     */
    public int threadCount = Runtime.getRuntime().availableProcessors() +1;

    public ImageLoaderConfig setThreadCount(int count) {
        this.threadCount = Math.max(1, count);
        return this;
    }

    public ImageLoaderConfig setCache(BitmapCache cache) {
        this.bitmapCache = cache;
        return this;
    }

    public ImageLoaderConfig setLoadingPlaceholder(int resId) {
        displayConfig.loadingResId  = resId;
        return this;
    }

    public ImageLoaderConfig setNotFoundPlaceholder(int resId) {
        displayConfig.failedResId = resId;
        return this;
    }

    public ImageLoaderConfig setLoadPolicy(LoadPolicy policy) {
        if (policy != null) {
            loadPolicy = policy;
        }
        return this;
    }
}
