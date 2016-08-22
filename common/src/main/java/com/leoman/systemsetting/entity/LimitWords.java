package com.leoman.systemsetting.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
@Table(name = "t_limit")
@Entity
public class LimitWords extends BaseEntity {

    @Column(name = "num")
    private Integer num;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
