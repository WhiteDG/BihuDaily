/*
 * Copyright 2017. Wh1te
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.white.bihudaily.module.dailys;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.white.bihudaily.R;
import com.white.bihudaily.app.BihuDailyApplication;
import com.white.bihudaily.app.Constant;
import com.white.bihudaily.base.BaseWithToolbarActivity;
import com.white.bihudaily.module.dailys.theme.ThemesFragment;
import com.white.bihudaily.utils.ActivityUtils;
import com.white.bihudaily.utils.SPUtils;

import butterknife.BindView;

public class DailyActivity extends BaseWithToolbarActivity<DailyContract.Presenter> {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.nav_view)
    NavigationView navView;

//    @BindView(R.id.ll_drawer)
//    LinearLayout llDrawer;

    private TextView tvMyStar;
    private TextView tvOfflineDownload;
    private LinearLayout llNavUser;


    private DailyFragment mDailyFragment;
    private Fragment currentFragment;
    private String currentTag;

    @Override
    protected void beforeContentView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dailys;
    }

    @Override
    protected void prepareData(Intent intent) {

    }

    @Override
    protected void initView() {
        initToolbar();
        initDrawerLayout();
    }

    private void initFragment(String tag, String title) {
        currentTag = tag;
        if (tag.equals(Constant.TAG_MAIN)) {
            mDailyFragment =
                    (DailyFragment) getSupportFragmentManager().findFragmentByTag(Constant.TAG_MAIN);
            if (mDailyFragment == null) {
                // 新建一个fragment
                mDailyFragment = DailyFragment.newInstance();
                currentFragment = mDailyFragment;
                ActivityUtils.addAndShowFragmentToActivity(
                        getSupportFragmentManager(), mDailyFragment, null, Constant.TAG_MAIN, R.id.contentFrame);
            }
        } else {
            ThemesFragment themesFragment = ThemesFragment.newInstance(Integer.parseInt(tag));
            currentFragment = themesFragment;
            switchFragment(themesFragment, tag, title);
        }
    }

    private void initDrawerLayout() {
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
//        assert navView != null;
        setupDrawerContent(navView);
        View view = navView.getHeaderView(0);
//        assert llDrawer != null;
//        setupDrawerContent(llDrawer);
        tvMyStar = (TextView) view.findViewById(R.id.tv_my_star);
        tvOfflineDownload = (TextView) view.findViewById(R.id.tv_offline_download);
        llNavUser = (LinearLayout) view.findViewById(R.id.ll_nav_user);
    }

//    private void setupDrawerContent(LinearLayout llDrawer) {
//        tvMyStar = (TextView) llDrawer.findViewById(R.id.tv_my_star);
//        tvOfflineDownload = (TextView) llDrawer.findViewById(R.id.tv_offline_download);
//        llNavUser = (LinearLayout) llDrawer.findViewById(R.id.ll_nav_user);
//
//        RecyclerView rvThemeList = (RecyclerView) llDrawer.findViewById(R.id.rv_theme_list);
//        rvThemeList.setLayoutManager(new LinearLayoutManager(this));
//        rvThemeList.setAdapter(new ThemeListAdapter());
//    }

    @Override
    protected DailyContract.Presenter createPresenter() {
        return null;
    }

    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        setToolbarTitle(R.string.index);
        assert ab != null;
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initListener() {
        tvMyStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.toMyStarActivity(DailyActivity.this);
            }
        });

        tvOfflineDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("离线下载尚未开发完成");
            }
        });

        llNavUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.toLoginActivity(DailyActivity.this);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String title = savedInstanceState.getString(Constant.KEY_TITLE, "首页");
            setToolbarTitle(title);
            currentTag = savedInstanceState.getString(Constant.KEY_TAG, Constant.TAG_MAIN);
            initFragment(currentTag, title);
        } else {
            initFragment(Constant.TAG_MAIN, getResources().getString(R.string.index));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 主题改变时保存相关数据
        outState.putString(Constant.KEY_TAG, currentTag);
        outState.putString(Constant.KEY_TITLE, getSupportActionBar().getTitle().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if ((boolean) SPUtils.get(this, Constant.KEY_NIGHT, false)) {
            menu.getItem(1).setTitle(R.string.action_day_mode);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_setting:
                ActivityUtils.toSettingActivity(this);
                break;
            case R.id.action_night:
                int uiMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                switch (uiMode) {
                    case Configuration.UI_MODE_NIGHT_NO://当前日间模式
                        SPUtils.put(DailyActivity.this, Constant.KEY_NIGHT, true);
                        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                    case Configuration.UI_MODE_NIGHT_YES://当前夜间模式
                        SPUtils.put(DailyActivity.this, Constant.KEY_NIGHT, false);
                        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        break;
                }
                getSupportFragmentManager().getFragments().clear();
                recreate();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int themeId = menuItem.getOrder();
                        switch (menuItem.getItemId()) {
                            case R.id.menu_main_page:
                                // 主页
                                mDailyFragment = (DailyFragment) getSupportFragmentManager().findFragmentByTag(Constant.TAG_MAIN);
                                if (mDailyFragment == null) {
                                    mDailyFragment = new DailyFragment();
                                }
                                switchFragment(mDailyFragment, Constant.TAG_MAIN, getResources().getString(R.string.index));
                                break;
                            default:
                                // 主题日报内容列表界面
                                ThemesFragment themesFragment = (ThemesFragment) getSupportFragmentManager().findFragmentByTag(themeId + "");
                                if (themesFragment == null) {
                                    themesFragment = ThemesFragment.newInstance(themeId);
                                }
                                switchFragment(themesFragment, themeId + "", menuItem.getTitle().toString());
                                break;

                        }
                        // 关闭侧滑菜单
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (currentFragment != mDailyFragment) {// 不在主页时先返回主页
                navView.setCheckedItem(R.id.menu_main_page);
                ActivityUtils.showFragmentToActivity(getSupportFragmentManager(), mDailyFragment, currentFragment);
                currentFragment = mDailyFragment;
                setToolbarTitle(R.string.index);
            } else {
                BihuDailyApplication.exitApp();
            }
        }

    }

    /**
     * 切换fragment
     *
     * @param show  前台显示的fragment
     * @param tag   fragment的tag
     * @param title fragment的title
     */
    private void switchFragment(Fragment show, String tag, String title) {
        currentTag = tag;
        if (!show.isAdded()) {
            getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                    hide(currentFragment).add(R.id.contentFrame, show, tag).show(show).commit();
        } else {
            getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                    hide(currentFragment).show(show).commit();
        }
        currentFragment = show;
        setToolbarTitle(title);
    }
}
