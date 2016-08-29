package com.white.bihudaily.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.white.bihudaily.BasePresenter;
import com.white.bihudaily.BaseView;
import com.white.bihudaily.R;
import com.white.bihudaily.app.BihuDailyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author White
 * Date 2016/8/13
 * Time 11:19
 */
public abstract class BaseWithToolbarActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView<P> {

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

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
        progressDialog = new ProgressDialog(this);
        initView();
        initListener();
        initData(savedInstanceState);

    }

    protected abstract P createPresenter();


    protected void showProgressDialog(String msg) {
        progressDialog.setMessage(msg);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                // TODO: 2016/8/21 取消dialog显示后执行什么操作
            }
        });
        progressDialog.show();
    }

    protected void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


    @Override
    public void setPresenter(P presenter) {
        mPresenter = presenter;
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

    public void setToolbarTitle(String msg) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(msg);
        }
    }

    public String getToolbarTitle() {
        if (getSupportActionBar() != null) {
            return getSupportActionBar().getTitle().toString();
        }
        return getTitle().toString();
    }

    public void setToolbarTitle(int titleId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titleId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
        BihuDailyApplication.removeActivity(this);
    }

    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    protected void initToolbar(int titleId) {
        initToolbar();
        setToolbarTitle(titleId);
    }

    protected void initToolbar(String title) {
        initToolbar();
        setToolbarTitle(title);
    }

    protected abstract void beforeContentView();

    protected abstract int getLayoutId();

    protected abstract void prepareData(Intent intent);

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData(Bundle savedInstanceState);


}
