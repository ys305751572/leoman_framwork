package com.leoman.userchange.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.user.entity.UserInfo;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/12.
 */
@Table(name = "t_user_change")
@Entity
public class UserChange extends BaseEntity {

    @Column(name = "source_id")
    private Long sourceId;

    @Column(name = "type")
    private Integer type;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
