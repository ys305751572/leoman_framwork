package com.leoman.role.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_role")
@Entity
public class Role extends BaseEntity {

    @Column(name = "name")
    private String name = "";

    @Transient
    private Integer count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
