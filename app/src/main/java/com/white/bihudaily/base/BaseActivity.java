package com.white.bihudaily.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.white.bihudaily.BasePresenter;
import com.white.bihudaily.app.BihuDailyApplication;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author White
 * Date 2016/8/20
 * Time 21:43
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    private Unbinder bind;
    private ProgressDialog progressDialog;
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BihuDailyApplication.addActivity(this);
        beforeContentView();
        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        mPresenter = createPresenter();
        prepareData(getIntent());
        initView();
        progressDialog = new ProgressDialog(this);
        initListener();
        initData(savedInstanceState);
    }

    protected abstract P createPresenter();


    protected void showProgressDialog(String msg) {
        progressDialog.setMessage(msg);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    protected void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void showSnackBar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    public void showSnackBar(View view, int msgId) {
        Snackbar.make(view, msgId, Snackbar.LENGTH_SHORT).show();
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int msgId) {
        Toast.makeText(this, msgId, Toast.LENGTH_SHORT).show();
    }

    protected void setToolbarTitle(String msg) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(msg);
        }
    }

    protected void setToolbarTitle(int titleId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titleId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
        BihuDailyApplication.removeActivity(this);
    }

    protected abstract void beforeContentView();

    protected abstract int getLayoutId();

    protected abstract void prepareData(Intent intent);

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData(Bundle savedInstanceState);
}
