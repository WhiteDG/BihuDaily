package com.white.bihudaily.bean;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Author White
 * Date 2016/8/13
 * Time 15:04
 */
public class Comments implements Parcelable {
    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.comments);
    }

    public Comments() {
    }

    protected Comments(Parcel in) {
        this.comments = in.createTypedArrayList(Comment.CREATOR);
    }

    public static final Parcelable.Creator<Comments> CREATOR = new Parcelable.Creator<Comments>() {
        @Override
        public Comments createFromParcel(Parcel source) {
            return new Comments(source);
        }

        @Override
        public Comments[] newArray(int size) {
            return new Comments[size];
        }
    };
}
