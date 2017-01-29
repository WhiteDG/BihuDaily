package com.white.bihudaily.module.settings;


import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.white.bihudaily.R;
import com.white.bihudaily.utils.CacheCleanUtil;
import com.white.bihudaily.utils.IntentUtils;

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        Preference clear_cache = getPreferenceManager().findPreference(getResources().getString(R.string.key_clear_cache));
        Preference notice = getPreferenceManager().findPreference(getResources().getString(R.string.key_notice));
        Preference developer = getPreferenceManager().findPreference(getResources().getString(R.string.key_developer));

        clear_cache.setOnPreferenceClickListener(this);
        notice.setOnPreferenceClickListener(this);
        developer.setOnPreferenceClickListener(this);

    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case "clear_cache":
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.title_clear_cache).setMessage("当前缓存大小: " + CacheCleanUtil.getTotalCacheSize())
                        .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("清除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CacheCleanUtil.cleanAllCache();
                                Toast.makeText(getActivity(), R.string.toast_clear_cache, Toast.LENGTH_SHORT).show();
                            }
                        }).show();

                break;

            case "notice":
                AlertDialog.Builder noticeBuilder = new AlertDialog.Builder(getActivity());
                noticeBuilder.setTitle(R.string.title_notice).setMessage("本软件仅作学习之用，数据来自知乎日报官方，API为本人未经知乎日报官方同意采用非正当手段获取，" +
                        "如被告知侵权或属于不正当使用，本人将立即停止对知乎日报API的使用以及下架本软件。")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();

                break;

            case "developer":
                AlertDialog.Builder developerBuilder = new AlertDialog.Builder(getActivity());
                developerBuilder.setTitle("开发者").setItems(new CharSequence[]{"GitHub: WhiteDG", "Email: white.hcj@gmail.com"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                IntentUtils.openBrowser(getActivity(), "https://github.com/WhiteDG/BihuDaily");
                                break;
                            case 1:
                                IntentUtils.openMailApp(getActivity(), "white.hcj@gmail.com", "逼乎日报反馈", "Hi");
                                break;
                        }
                    }
                }).show();

                break;
        }
        return false;
    }
}
