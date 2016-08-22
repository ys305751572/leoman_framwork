package com.leoman.work.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_works_comic")
@Entity
public class WorkComic extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "works_id")
    private Work work;

    @Column(name = "series")
    private Integer series;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "playNum")
    private Integer playNum;

    @Column(name = "barrageNum")
    private Integer barrageNum;

    @Transient
    private Integer imageList;

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPlayNum() {
        return playNum;
    }

    public void setPlayNum(Integer playNum) {
        this.playNum = playNum;
    }

    public Integer getBarrageNum() {
        return barrageNum;
    }

    public void setBarrageNum(Integer barrageNum) {
        this.barrageNum = barrageNum;
    }

    public Integer getImageList() {
        return imageList;
    }

    public void setImageList(Integer imageList) {
        this.imageList = imageList;
    }
}
