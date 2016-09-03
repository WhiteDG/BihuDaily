/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.white.bihudaily.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.white.bihudaily.app.Constant;
import com.white.bihudaily.app.BihuDailyApplication;
import com.white.bihudaily.bean.Editor;
import com.white.bihudaily.bean.Story;
import com.white.bihudaily.bean.StoryExtra;
import com.white.bihudaily.bean.Theme;
import com.white.bihudaily.bigimage.ImageViewActivity;
import com.white.bihudaily.comment.CommentActivity;
import com.white.bihudaily.dailys.DailyActivity;
import com.white.bihudaily.detail.DetailActivity;
import com.white.bihudaily.detail.ThemeDetailActivity;
import com.white.bihudaily.editor.EditorActivity;
import com.white.bihudaily.editor.EditorInfoActivity;
import com.white.bihudaily.login.LoginActivity;
import com.white.bihudaily.mystar.MyStarActivity;
import com.white.bihudaily.settings.SettingActivity;

import java.io.Serializable;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * 提供帮助Activity加载UI的方法
 */
public class ActivityUtils {

    /**
     * 添加fragment到Activity中
     *
     * @param fragmentManager
     * @param showFragment
     * @param frameId
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment showFragment, Fragment hideFragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(showFragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (hideFragment != null) {
            transaction.hide(hideFragment);
        }
        transaction.add(frameId, showFragment);
        transaction.show(showFragment);
        transaction.commit();
    }

    public static void addAndShowFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                                    @NonNull Fragment showFragment,
                                                    Fragment hideFragment,
                                                    @NonNull String tag,
                                                    int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(showFragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (hideFragment != null) {
            transaction.hide(hideFragment);
        }
        transaction.add(frameId, showFragment, tag);
        transaction.show(showFragment);
        transaction.commit();
    }

    public static void showFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment showFragment, @NonNull Fragment hideFragment) {
        checkNotNull(fragmentManager);
        checkNotNull(showFragment);
        Log.e(BihuDailyApplication.TAG, "showFragmentToActivity");
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.hide(hideFragment);
        transaction.show(showFragment);
        transaction.commit();
    }


    public static void toDailysActivity(Context context) {
        Intent intent = new Intent(context, DailyActivity.class);
        context.startActivity(intent);
    }


    public static void setVisible(boolean visible, View... view) {
        if (view != null) {
            if (visible) {
                for (View v : view) {
                    if (v != null)
                        v.setVisibility(View.VISIBLE);
                }
            } else {
                for (View v : view) {
                    if (v != null)
                        v.setVisibility(View.GONE);
                }
            }
        }
    }

    public static void toStoryDetailActivity(Context context, Story story) {
        Intent intent = new Intent(context, DetailActivity.class);
//        intent.putExtra(Constant.STORY_ID, storyId);
        intent.putExtra(Constant.STORY, story);
        context.startActivity(intent);
    }

    public static void toThemeDeatilsActivity(Context context, Story story) {
        Intent intent = new Intent(context, ThemeDetailActivity.class);
        intent.putExtra(Constant.STORY, story);
        context.startActivity(intent);
    }

    public static void toEditorListActivity(Context context, Theme theme) {
        Intent intent = new Intent(context, EditorActivity.class);
        intent.putExtra(Constant.THEME, theme);
        context.startActivity(intent);
    }

    public static void toCommentActivity(Context context, int storyId, StoryExtra storyExtra) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra(Constant.STORY_ID, storyId);
        if (storyExtra != null) {
            intent.putExtra(Constant.COMMENT_COUNT, storyExtra.getComments());
            intent.putExtra(Constant.LONG_COMMENT_COUNT, storyExtra.getLong_comments());
            intent.putExtra(Constant.SHORT_COMMENT_COUNT, storyExtra.getShort_comments());
        }
        context.startActivity(intent);
    }

    public static void toMyStarActivity(Context context) {
        Intent intent = new Intent(context, MyStarActivity.class);
        context.startActivity(intent);
    }

    public static void toEditorInfoActivity(Context context, Editor editor) {
        Intent intent = new Intent(context, EditorInfoActivity.class);
        intent.putExtra(Constant.EDITOR, editor);
        context.startActivity(intent);
    }

    public static void toLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void toImageViewActivity(Activity activity, String imgPath, List<String> imgUrlList) {
        Intent intent = new Intent(activity, ImageViewActivity.class);
        intent.putExtra(Constant.IMG_URL, imgPath);
        intent.putExtra(Constant.IMG_URL_LIST, (Serializable) imgUrlList);
        ActivityCompat.startActivity(activity, intent, null);
    }

    public static void toSettingActivity(Context context) {
        context.startActivity(new Intent(context, SettingActivity.class));
    }
}
