package com.leoman.work.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.user.entity.UserInfo;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_works_creator")
@Entity
public class WorkCreator extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "works_id")
    private Work work;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    @Column(name = "praise")
    private Integer praise;

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Integer getPraise() {
        return praise;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }
}
