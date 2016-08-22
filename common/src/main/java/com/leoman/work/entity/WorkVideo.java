package com.leoman.work.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_works_video")
@Entity
public class WorkVideo extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "works_id")
    private Work work;

    @Column(name = "series")
    private Integer series;

    @Column(name = "link_url")
    private String linkUrl;

    @Column(name = "name")
    private String name;

    @Column(name = "length")
    private Long length;

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

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }
}
