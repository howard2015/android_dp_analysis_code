package org.simple.imageloader.policy;

import org.simple.imageloader.request.BitmapRequest;

/**
 * Created by dorian_d on 2017/12/8.
 */

public class SerialPolicy implements LoadPolicy {
    @Override
    public int compare(BitmapRequest request1, BitmapRequest request2) {
        // 那么按照添加到队列的序列号顺序来执行
        return request1.serialNum - request2.serialNum;
    }
}
