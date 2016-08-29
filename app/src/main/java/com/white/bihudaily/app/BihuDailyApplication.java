package com.white.bihudaily.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Toast;

import com.white.bihudaily.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Author White
 * Date 2016/8/12
 * Time 15:31
 */

public class BihuDailyApplication extends Application {

    public static final String TAG = "BihuDaily";
    private static List<Activity> activityList;
    public static Context appContext;


    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        // 设置夜间模式
        boolean isNightMode = (boolean) SPUtils.get(getApplicationContext(), Constant.KEY_NIGHT, false);
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

    }


    public static void addActivity(Activity activity) {
        if (activityList == null) {
            activityList = new ArrayList<>();
        }
        if (!activityList.contains(activity)) {
            activityList.add(activity);
        }
    }

    public static void removeActivity(Activity activity) {
        if (activityList != null && activityList.contains(activity)) {
            activityList.remove(activity);
        }
    }

    public static void clearActivity() {
        if (activityList != null) {
            for (Activity activity : activityList) {
                activity.finish();
            }
            activityList = null;
        }
    }

    /**
     * 退出软件
     */
    private static long exitTime = 0;

    public static void exitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(appContext, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            //退出应用
            clearActivity();
        }
    }
}
