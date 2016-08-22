package com.leoman.work.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.user.entity.UserInfo;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_comment_works")
@Entity
public class WorkComment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private UserInfo userInfo;

    @ManyToOne
    @JoinColumn(name = "works_id")
    private Work work;

    @Column(name = "to_user_id")
    private Long toUserId;

    @Column(name = "type")
    private Integer type;

    @Column(name = "praise")
    private Integer praise;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private Integer typePlus;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPraise() {
        return praise;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTypePlus() {
        return typePlus;
    }

    public void setTypePlus(Integer typePlus) {
        this.typePlus = typePlus;
    }
}
