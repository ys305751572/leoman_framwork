package com.leoman.systemsetting.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_prize")
@Entity
public class Prize extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "coin")
    private Integer coin;

    @Column(name = "pro")
    private Double pro;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public Double getPro() {
        return pro;
    }

    public void setPro(Double pro) {
        this.pro = pro;
    }
}
