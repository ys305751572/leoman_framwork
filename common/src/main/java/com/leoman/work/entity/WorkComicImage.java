package com.leoman.work.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_works_comic_image")
@Entity
public class WorkComicImage extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "comic_id")
    private WorkComic workComic;

    @Column(name = "url")
    private String url;

    public WorkComic getWorkComic() {
        return workComic;
    }

    public void setWorkComic(WorkComic workComic) {
        this.workComic = workComic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
