package com.leoman.common.log.entity;


import com.leoman.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/5/22.
 */
@Entity
@Table(name = "t_log")
public class LogEntity extends BaseEntity {

    @Column(name = "message")
    private String message;

    @Column(name = "user_id")
    private Long userId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
