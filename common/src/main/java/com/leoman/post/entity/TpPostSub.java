package com.leoman.post.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_post_tp_sub")
@Entity
public class TpPostSub extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostBase postBase;

    @Column(name = "name")
    private String name;

    @Column(name = "cover")
    private String cover;

    @Column(name = "count")
    private Integer count;

    @Transient
    private Integer status;

    public PostBase getPostBase() {
        return postBase;
    }

    public void setPostBase(PostBase postBase) {
        this.postBase = postBase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
