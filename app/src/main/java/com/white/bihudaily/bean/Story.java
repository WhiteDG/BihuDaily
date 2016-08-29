package com.white.bihudaily.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author White
 * Date 2016/8/13
 * Time 14:46
 */
public class Story extends AdapterBean implements Serializable {

//    public static final int TYPE_TITLE = 1;
//    public static final int TYPE_FOOTER = 2;
//    public static final int TYPE_HEADER = 3;
    public static final int TYPE_STORY = 0;
    public static final int TYPE_NO_IMG_STORY = 4;

    private String title;// 新闻标题
    private List<String> images; // 图像地址（官方 API 使用数组形式。目前暂未有使用多张图片的情形出现，曾见无 images 属性的情况，请在使用中注意 ）
    private String ga_prefix; // 供 Google Analytics 使用
    //    private int type; // 作用未知
    private int id; // url 与 share_url 中最后的数字（应为内容的 id）
    private boolean multipic; // 消息是否包含多张图片（仅出现在包含多图的新闻中）
    private String date;
//    private int showType;

    public Story(int id, String title, String image, String date, boolean multipic) {

        this(id, title, image, date);
        this.multipic = multipic;
    }

    public boolean isHaveImg() {
        return haveImg;
    }

    public void setHaveImg(boolean haveImg) {
        this.haveImg = haveImg;
    }

    private boolean haveImg;

    public Story() {

    }

    public Story(String date, int showType) {
        this.date = date;
        this.showType = showType;
    }

    public Story(int id, String title, String image, String date) {
        this.id = id;
        this.title = title;
        if (image == null || image.length() == 0 || image.equals("")) {
            setShowType(Story.TYPE_NO_IMG_STORY);
            this.haveImg = false;
        } else {
            this.haveImg = true;
            images = new ArrayList<>();
            images.add(image);
        }
        this.date = date;
    }

    public Story(int typeFooter) {
        this.showType = typeFooter;
    }

//    public int getShowType() {
//        return showType;
//    }
//
//    public void setShowType(int showType) {
//        this.showType = showType;
//    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public boolean isMultipic() {
        return multipic;
    }

    public void setMultipic(boolean multipic) {
        this.multipic = multipic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Story{" +
                "title='" + title + '\'' +
                ", images=" + images +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", id=" + id +
                ", date='" + date + '\'' +
                ", showType=" + showType +
                '}';
    }
}
