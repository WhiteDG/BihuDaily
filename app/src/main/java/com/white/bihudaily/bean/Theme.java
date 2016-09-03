package com.white.bihudaily.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Author White
 * Date 2016/8/16
 * Time 13:26
 */
public class Theme implements Parcelable {
    private List<Story> stories;
    private String description;
    private String background;
    private String color;
    private String name;
    private String image;
    private List<Editor> editors;
    private String image_source;

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Editor> getEditors() {
        return editors;
    }

    public void setEditors(List<Editor> editors) {
        this.editors = editors;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.stories);
        dest.writeString(this.description);
        dest.writeString(this.background);
        dest.writeString(this.color);
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeTypedList(this.editors);
        dest.writeString(this.image_source);
    }

    public Theme() {
    }

    protected Theme(Parcel in) {
        this.stories = in.createTypedArrayList(Story.CREATOR);
        this.description = in.readString();
        this.background = in.readString();
        this.color = in.readString();
        this.name = in.readString();
        this.image = in.readString();
        this.editors = in.createTypedArrayList(Editor.CREATOR);
        this.image_source = in.readString();
    }

    public static final Parcelable.Creator<Theme> CREATOR = new Parcelable.Creator<Theme>() {
        @Override
        public Theme createFromParcel(Parcel source) {
            return new Theme(source);
        }

        @Override
        public Theme[] newArray(int size) {
            return new Theme[size];
        }
    };
}
