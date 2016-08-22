package com.leoman.floatwin.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.work.entity.Work;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_float_win")
@Entity
public class FloatWin extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "cover")
    private String cover;

    @Column(name = "position")
    private Integer position;

    @Column(name = "work_id")
    private Long categoryId;

    @Column(name = "detail_id")
    private Long detailId;

    @Column(name = "status")
    private Integer status = 0;

    @Column(name = "time")
    private Long time;

    @Transient
    private Long workId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }
}
