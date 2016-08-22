package com.leoman.welfare.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.user.entity.UserInfo;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_user_welfare")
@Entity
public class UserWelfare extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    @ManyToOne
    @JoinColumn(name = "welfare_id")
    private Welfare welfare;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Welfare getWelfare() {
        return welfare;
    }

    public void setWelfare(Welfare welfare) {
        this.welfare = welfare;
    }
}
