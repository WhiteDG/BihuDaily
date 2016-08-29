package com.white.bihudaily.mystar;

import android.content.Context;
import android.support.annotation.NonNull;

import com.white.bihudaily.BasePresenterImpl;
import com.white.bihudaily.bean.Story;
import com.white.bihudaily.data.MyStarSource;

import java.util.List;

/**
 * Author White
 * Date 2016/8/18
 * Time 17:46
 */
public class MyStarPresenter extends BasePresenterImpl<MyStarSource, MyStarContract.View> implements MyStarContract.Presenter {


    public MyStarPresenter(MyStarSource source, MyStarContract.View view) {
        super(source, view);
    }

    @Override
    public void loadStories(Context context) {
        List<Story> starList = mSource.getStarList(context);
        mView.showStories(starList);
    }

    @Override
    public void openStoryDetails(@NonNull Story item) {
        mView.showStoryDetailsUi(item);
    }

    @Override
    public void markReader(Context context, int id) {
        mSource.saveReader(context, id);
    }

    @Override
    public List<Integer> getReaderList(Context context) {
        return mSource.getReader(context);
    }

}
