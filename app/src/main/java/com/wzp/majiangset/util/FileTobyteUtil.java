package com.wzp.majiangset.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by su823 on 18/6/28.
 */

public class FileTobyteUtil {

    public static  byte[] binToByte(Context c, byte[] bytesFile) {
        //创建byte数组

        AssetManager manager = c.getResources().getAssets();
        InputStream inputStream =null;
        try {
            inputStream = manager.open("jiami.bin");
            int lenght = inputStream.available();
            byte[] buffer = new byte[lenght];
//将文件中的数据读到byte数组中
            inputStream.read(bytesFile);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        return bytesFile;
    }
/*
    public static  byte[] ReadIncludedFileBinToByte(Context c) {
        //创建byte数组

        AssetManager manager = c.getResources().getAssets();
        InputStream inputStream =null;
        List list =new ArrayList();
        try {
            inputStream = manager.open("jiami.bin");

            int lenght = inputStream.available();
            byte[] buffer = new byte[lenght];
//将文件中的数据读到byte数组中
            inputStream.read(buffer);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        return  list.toArray();
    }*/
    public static byte[] intToByteArray(int a) {
        return new byte[]{
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };

    }
}
