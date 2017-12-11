package org.simple.imageloader.loader;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import org.simple.imageloader.cache.BitmapCache;
import org.simple.imageloader.config.DisplayConfig;
import org.simple.imageloader.core.SimpleImageLoader;
import org.simple.imageloader.request.BitmapRequest;

/**
 * Created by dorian_d on 2017/12/8.
 */

public abstract  class AbsLoader implements Loader{

    /**
     * 图片缓存
     */
    private static BitmapCache mCache = SimpleImageLoader.getInstance().getConfig().bitmapCache;

    @Override
    public final void loadImage(BitmapRequest request) {
        // 1、从缓存中获取
        Bitmap resultBitmap = mCache.get(request);
        Log.e("", "### 是否有缓存 : " + resultBitmap + ", uri = " + request.imageUri);
        if (resultBitmap == null) {
            showLoading(request);
            // 2、没有缓存，调用onLoaderImage加载图片
            resultBitmap = onLoadImage(request);
            // 3、缓存图片
            cacheBitmap(request, resultBitmap);
        } else {
            request.justCacheInMem = true;
        }
        // 4、将结果投递到UI线程
        deliveryToUIThread(request, resultBitmap);
    }

    /** 加载图片的hook方法，留给子类处理
     * @param request
     * @return
     */
    protected abstract Bitmap onLoadImage(BitmapRequest request);

    /**
     * @param request
     * @param bitmap
     */
    private void cacheBitmap(BitmapRequest request, Bitmap bitmap) {
        // 缓存新的图片
        if (bitmap != null && mCache != null) {
            synchronized (mCache) {
                mCache.put(request, bitmap);
            }
        }
    }

    /**
     * 显示加载中的视图,注意这里也要判断imageview的tag与image uri的相等性,否则逆序加载时出现问题
     *
     * @param request
     */
    protected void showLoading(final BitmapRequest request) {
        final ImageView imageView = request.getImageView();
        if (request.isImageViewTagValid()
                && hasLoadingPlaceholder(request.displayConfig)) {
            imageView.post(new Runnable() {

                @Override
                public void run() {
                    imageView.setImageResource(request.displayConfig.loadingResId);
                }
            });
        }
    }

    /**
     * 将结果投递到UI,更新ImageView
     *
     * @param request
     * @param bitmap
     */
    protected void deliveryToUIThread(final BitmapRequest request,
                                      final Bitmap bitmap) {
        final ImageView imageView = request.getImageView();
        if (imageView == null) {
            return;
        }
        imageView.post(new Runnable() {

            @Override
            public void run() {
                updateImageView(request, bitmap);
            }
        });
    }

    /**
     * 更新ImageView
     *
     * @param request
     * @param result
     */
    private void updateImageView(BitmapRequest request, Bitmap result) {
        final ImageView imageView = request.getImageView();
        final String uri = request.imageUri;
        if (result != null && imageView.getTag().equals(uri)) {
            imageView.setImageBitmap(result);
        }

        // 加载失败
        if (result == null && hasFaildPlaceholder(request.displayConfig)) {
            imageView.setImageResource(request.displayConfig.failedResId);
        }

        // 回调接口
        if (request.imageListener != null) {
            request.imageListener.onComplete(imageView, result, uri);
        }
    }

    private boolean hasLoadingPlaceholder(DisplayConfig displayConfig) {
        return displayConfig != null && displayConfig.loadingResId > 0;
    }

    private boolean hasFaildPlaceholder(DisplayConfig displayConfig) {
        return displayConfig != null && displayConfig.failedResId > 0;
    }
}
