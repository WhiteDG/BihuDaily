package com.white.bihudaily.utils;

import android.os.Environment;
import android.text.format.Formatter;

import com.white.bihudaily.app.BihuDailyApplication;

import java.io.File;
import java.math.BigDecimal;

/**
 * Author White
 * Date 2016/8/23
 * Time 16:47
 */
public class CacheCleanUtil {

    public static String getTotalCacheSzie() {
        long cacheSize = getFolderSize(BihuDailyApplication.appContext.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(BihuDailyApplication.appContext.getExternalCacheDir());
        }
        return Formatter.formatFileSize(BihuDailyApplication.appContext, cacheSize);
    }

    public static void cleanAllCache() {
        deleteDir(BihuDailyApplication.appContext.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(BihuDailyApplication.appContext.getExternalCacheDir());
        }
    }

    private static boolean deleteDir(File cacheDir) {
        if (cacheDir != null && cacheDir.isDirectory()) {
            String[] children = cacheDir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(cacheDir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        return cacheDir != null ? cacheDir.delete() : false;
    }

    private static String getFormatSize(long cacheSize) {
        double kiloByte = cacheSize / 1024;
        if (kiloByte < 1) {
            return cacheSize + "B";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "K";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "M";
        }
        double teraByte = gigaByte / 1024;
        if (teraByte < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(megaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "G";

        }
        return "没有缓存";
    }

    private static long getFolderSize(File file) {
        long size = 0;
        File[] fileList = file.listFiles();
        for (File aFileList : fileList) {
            if (aFileList.isDirectory()) {
                size = size + getFolderSize(aFileList);
            } else {
                size = size + aFileList.length();
            }
        }
        return size;
    }
}
