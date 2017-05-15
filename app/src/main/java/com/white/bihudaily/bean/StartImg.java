package com.white.bihudaily.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author White
 * Date 2016/8/13
 * Time 12:59
 */
public class StartImg implements Parcelable {

    private String img;
    private String text;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.img);
        dest.writeString(this.text);
    }

    public StartImg() {
    }

    protected StartImg(Parcel in) {
        this.img = in.readString();
        this.text = in.readString();
    }

    public static final Parcelable.Creator<StartImg> CREATOR = new Parcelable.Creator<StartImg>() {
        @Override
        public StartImg createFromParcel(Parcel source) {
            return new StartImg(source);
        }

        @Override
        public StartImg[] newArray(int size) {
            return new StartImg[size];
        }
    };
}
