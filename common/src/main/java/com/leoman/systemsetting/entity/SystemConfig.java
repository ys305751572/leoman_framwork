package com.leoman.systemsetting.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_system_config")
@Entity
public class SystemConfig extends BaseEntity {

    @Column(name = "type")
    private String type;

    @Column(name = "content")
    private String content;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
