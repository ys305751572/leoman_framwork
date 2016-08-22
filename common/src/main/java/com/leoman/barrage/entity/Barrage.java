package com.leoman.barrage.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.user.entity.UserInfo;
import com.leoman.work.entity.WorkComic;
import com.leoman.work.entity.WorkVideo;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_barrage")
@Entity
public class Barrage extends BaseEntity {

    @Column(name = "type")
    private Integer type;

    @Column(name = "works_id")
    private Long workId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    @Column(name = "praise")
    private Long praise;

    @Column(name = "content")
    private String content;

    @Column(name = "time")
    private Integer time;

    @Transient
    private WorkVideo workVideo;

    @Transient
    private WorkComic workComic;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public Long getPraise() {
        return praise;
    }

    public void setPraise(Long praise) {
        this.praise = praise;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public WorkVideo getWorkVideo() {
        return workVideo;
    }

    public void setWorkVideo(WorkVideo workVideo) {
        this.workVideo = workVideo;
    }

    public WorkComic getWorkComic() {
        return workComic;
    }

    public void setWorkComic(WorkComic workComic) {
        this.workComic = workComic;
    }
}
