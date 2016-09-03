package com.white.bihudaily.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Author White
 * Date 2016/8/13
 * Time 14:43
 */
public class Latest implements Parcelable {
    private String date;
    private List<Story> stories;
    private List<TopStory> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public List<TopStory> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStory> top_stories) {
        this.top_stories = top_stories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeTypedList(this.stories);
        dest.writeList(this.top_stories);
    }

    public Latest() {
    }

    protected Latest(Parcel in) {
        this.date = in.readString();
        this.stories = in.createTypedArrayList(Story.CREATOR);
        this.top_stories = new ArrayList<TopStory>();
        in.readList(this.top_stories, TopStory.class.getClassLoader());
    }

    public static final Parcelable.Creator<Latest> CREATOR = new Parcelable.Creator<Latest>() {
        @Override
        public Latest createFromParcel(Parcel source) {
            return new Latest(source);
        }

        @Override
        public Latest[] newArray(int size) {
            return new Latest[size];
        }
    };
}
