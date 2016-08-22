package com.leoman.work.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_works_novel")
@Entity
public class WorkNovel extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "works_id")
    private Work work;

    @Column(name = "series")
    private Integer series;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "detail")
    private String detail;

    @Column(name = "length")
    private Integer length = 0;

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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
