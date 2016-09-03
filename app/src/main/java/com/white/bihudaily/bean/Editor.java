package com.white.bihudaily.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author White
 * Date 2016/8/16
 * Time 13:28
 */
public class Editor implements Parcelable {
    private String url;
    private String bio;
    private int id;
    private String avatar;
    private String name;

    @Override
    public String toString() {
        return "Editor{" +
                "url='" + url + '\'' +
                ", bio='" + bio + '\'' +
                ", id=" + id +
                ", avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.bio);
        dest.writeInt(this.id);
        dest.writeString(this.avatar);
        dest.writeString(this.name);
    }

    public Editor() {
    }

    protected Editor(Parcel in) {
        this.url = in.readString();
        this.bio = in.readString();
        this.id = in.readInt();
        this.avatar = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Editor> CREATOR = new Parcelable.Creator<Editor>() {
        @Override
        public Editor createFromParcel(Parcel source) {
            return new Editor(source);
        }

        @Override
        public Editor[] newArray(int size) {
            return new Editor[size];
        }
    };
}
