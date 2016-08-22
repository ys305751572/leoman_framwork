package com.leoman.adsindex.entity;

import com.leoman.category.entity.Category;
import com.leoman.entity.BaseEntity;
import com.leoman.work.entity.Work;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_ads_index")
@Entity
public class AdsIndex extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "cover")
    private String cover;

    @Column(name = "position")
    private Integer position;

    @Column(name = "work_id")
    private Long categoryId;

    @Column(name = "works_id")
    private Long workId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
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

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }
}
