package com.white.bihudaily.utils;

import android.content.Context;
import android.graphics.Bitmap;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Author White
 * Date 2016/8/13
 * Time 12:13
 */
public class CommonUtil {

    /**
     * 获取当前日期
     *
     * @return 返回格式 yyyyMMdd
     */
    public static String getToday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        return sdf.format(new Date());
    }

    /**
     * 获取用于显示的日期
     *
     * @return 返回格式 yyyy年MM月dd日
     */
    public static String getShowDate(String date) {
        if (date.length() != 8) {
            return "";
        }
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6, 8);
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(Integer.valueOf(year), Integer.valueOf(month) - 1, Integer.valueOf(day));
        String weekDay = new SimpleDateFormat("EEEE", Locale.CHINA).format(calendar.getTime());
        return year + "年" + month + "月" + day + "日 " + weekDay;
    }


    public static String md5(String paramString) {
        String returnStr;
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramString.getBytes());
            returnStr = byteToHexString(localMessageDigest.digest());
            return returnStr;
        } catch (Exception e) {
            return paramString;
        }
    }

    /**
     * 将指定byte数组转换成16进制字符串
     *
     * @param b
     * @return
     */
    public static String byteToHexString(byte[] b) {
        StringBuilder hexString = new StringBuilder();
        for (byte aB : b) {
            String hex = Integer.toHexString(aB & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getScreenPicHeight(int screenWidth, Bitmap bitmap) {
        int picWidth = bitmap.getWidth();
        int picHeight = bitmap.getHeight();
        int picScreenHeight;
        picScreenHeight = (screenWidth * picHeight) / picWidth;
        return picScreenHeight;
    }

    public static String timestamp2Date(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm", Locale.CHINA);
        return sdf.format(new Date(time * 1000));
    }
}
