package com.leoman.work.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_works_surround")
@Entity
public class WorkSurround extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "works_id")
    private Work work;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "link_url")
    private String linkUrl;

    @Column(name = "cover")
    private String cover;

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
