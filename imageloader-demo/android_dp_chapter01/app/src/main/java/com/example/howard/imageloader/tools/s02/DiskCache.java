package com.example.howard.imageloader.tools.s02;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dorian_d on 2017/12/6.
 */

public class DiskCache {
    static String cacheDir ="sdcard/cache/";
    public Bitmap get(String url){
        return BitmapFactory.decodeFile(cacheDir+url);
    }
    public void put(String url,Bitmap bitmap){
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream = new FileOutputStream(cacheDir+url);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }finally {
            if(fileOutputStream!=null){
               try {
                fileOutputStream.close();
               }catch (IOException e){
                   e.printStackTrace();
               }
            }
        }
    }
}
