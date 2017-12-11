package org.simple.imageloader.policy;

import org.simple.imageloader.request.BitmapRequest;

/**
 * Created by dorian_d on 2017/12/8.
 */

public class ReversePolicy implements LoadPolicy {
    @Override
    public int compare(BitmapRequest request1, BitmapRequest request2) {
        // 注意Bitmap请求要先执行最晚加入队列的请求,ImageLoader的策略
        return request2.serialNum - request1.serialNum;
    }
}
