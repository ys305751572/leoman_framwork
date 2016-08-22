package com.leoman.post.entity;

import com.leoman.user.entity.UserInfo;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_tp_post_user")
@Entity
public class TpPostUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tp_post_sub_id")
    private TpPostSub tpPostSub;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TpPostSub getTpPostSub() {
        return tpPostSub;
    }

    public void setTpPostSub(TpPostSub tpPostSub) {
        this.tpPostSub = tpPostSub;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
