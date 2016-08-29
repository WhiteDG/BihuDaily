package com.white.bihudaily.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.white.bihudaily.BasePresenter;
import com.white.bihudaily.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author White
 * Date 2016/8/13
 * Time 11:22
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment {

    protected Unbinder bind;
    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        View root = inflater.inflate(getFragmentLayout(), container, false);
        bind = ButterKnife.bind(this, root);
        prepareData();
        initView();
        initListener();
        getData();
        return root;
    }

    protected abstract P createPresenter();

    @SuppressWarnings("deprecation")
    protected void changeReadState(View clickedView, int textViewId) {
        TextView tvTitle = (TextView) clickedView.findViewById(textViewId);//R.id.tv_question_title
        tvTitle.setTextColor(getResources().getColor(R.color.textReader));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }

    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int msgId) {
        Toast.makeText(getContext(), msgId, Toast.LENGTH_SHORT).show();
    }

    public void showSnackBar(View view, int msgId) {
        Snackbar.make(view, msgId, Snackbar.LENGTH_SHORT).show();
    }

    public void showSnackBar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    protected abstract int getFragmentLayout();

    protected abstract void prepareData();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void getData();
}
