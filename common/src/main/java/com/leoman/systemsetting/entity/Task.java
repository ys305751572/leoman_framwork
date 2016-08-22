package com.leoman.systemsetting.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_task")
@Entity
public class Task extends BaseEntity {

    @Column(name = "type")
    private Integer type;

    @Column(name = "task_count")
    private Integer taskCount;

    @Column(name = "coin")
    private Integer coin;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(Integer taskCount) {
        this.taskCount = taskCount;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }
}
