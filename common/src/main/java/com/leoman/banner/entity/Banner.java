package com.leoman.banner.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.work.entity.Work;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_banner")
@Entity
public class Banner extends BaseEntity {

    @Column(name = "cover")
    private String cover;

    @Column(name = "link_url")
    private String linkUrl;

    @Column(name = "position")
    private Integer position;

    @Column(name = "work_id")
    private Long categoryId;

    @Column(name = "works_id")
    private Long workId;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    /*public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }*/

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
