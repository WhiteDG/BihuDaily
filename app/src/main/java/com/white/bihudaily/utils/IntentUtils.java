package com.white.bihudaily.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Author: Wh1te
 * Date: 2017-01-29
 */

public class IntentUtils {

    public static void openBrowser(Context context, String url) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        context.startActivity(intent);
    }

    public static void openMailApp(Context context, String targetMail, String theme, String content) {
        Uri uri = Uri.parse("mailto:" + targetMail);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra(Intent.EXTRA_SUBJECT, theme); // 主题
        intent.putExtra(Intent.EXTRA_TEXT, content); // 正文
        context.startActivity(Intent.createChooser(intent, "请选择邮件类应用"));
    }
}
