package com.leoman.image.entity;

/**
 * 图片实体类
 * Created by yesong on 2016/9/9.
 */
public class ImageVo {

    private String path;

    private Double width;

    private Double height;

    private String thumbs;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getThumbs() {
        return thumbs;
    }

    public void setThumbs(String thumbs) {
        this.thumbs = thumbs;
    }
}
