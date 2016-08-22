package com.leoman.creatordynamic.entity;

import com.leoman.creator.entity.Creator;
import com.leoman.entity.BaseEntity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_dynamic")
@Entity
public class Dynamic extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Creator creator;

    @Column(name = "time")
    private Long time;

    @Column(name = "status")
    private Integer status;

    @Column(name = "date")
    private String date;

    @Column(name = "source")
    private Integer source;

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }
}
