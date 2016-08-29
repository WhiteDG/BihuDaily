package com.white.bihudaily.bean;

import java.util.List;

/**
 * Author White
 * Date 2016/8/13
 * Time 15:07
 */
public class Themes {
    private int limit;
    private List<?> subscribed;
    private List<Other> others;


    public class Other {
        private String color;
        private String thumbnail;
        private String description;
        private int id;
        private String name;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
