package org.simple.imageloader.policy;

import org.simple.imageloader.request.BitmapRequest;

/**
 * Created by dorian_d on 2017/12/8.
 */

public interface LoadPolicy {
    public int compare(BitmapRequest request1, BitmapRequest request2);
}
