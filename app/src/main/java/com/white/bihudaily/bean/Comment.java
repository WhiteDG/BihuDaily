package com.white.bihudaily.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.white.bihudaily.utils.CommonUtil;

/**
 * Author White
 * Date 2016/8/13
 * Time 15:04
 */
public class Comment extends AdapterBean implements Parcelable {


    private String author;
    private int id;
    private String content;
    private int likes;
    private long time;
    private String avatar;


    public Comment() {

    }

    public Comment(int typeHeader) {
        showType = typeHeader;
    }

    public Comment(String content, int typeTitle) {
        this.content = content;
        showType = typeTitle;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getTime() {

        return CommonUtil.timestamp2Date(time);
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeInt(this.id);
        dest.writeString(this.content);
        dest.writeInt(this.likes);
        dest.writeLong(this.time);
        dest.writeString(this.avatar);
        dest.writeInt(this.showType);
    }

    protected Comment(Parcel in) {
        this.author = in.readString();
        this.id = in.readInt();
        this.content = in.readString();
        this.likes = in.readInt();
        this.time = in.readLong();
        this.avatar = in.readString();
        this.showType = in.readInt();
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
