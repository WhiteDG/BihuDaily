package com.white.bihudaily.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author White
 * Date 2016/8/13
 * Time 14:46
 */
public class TopStory implements Parcelable {
    private int id;
    private String title;
    private String ga_prefix;
    private String image;
    private int type;

    public TopStory(int id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TopStory{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", image='" + image + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.ga_prefix);
        dest.writeString(this.image);
        dest.writeInt(this.type);
    }

    protected TopStory(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.ga_prefix = in.readString();
        this.image = in.readString();
        this.type = in.readInt();
    }

    public static final Parcelable.Creator<TopStory> CREATOR = new Parcelable.Creator<TopStory>() {
        @Override
        public TopStory createFromParcel(Parcel source) {
            return new TopStory(source);
        }

        @Override
        public TopStory[] newArray(int size) {
            return new TopStory[size];
        }
    };
}
