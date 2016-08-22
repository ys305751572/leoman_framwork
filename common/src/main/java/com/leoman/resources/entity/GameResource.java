package com.leoman.resources.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.user.entity.UserInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_resource_game")
@Entity
public class GameResource extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "link_url")
    private String linkUrl;

    @Column(name = "version")
    private String version;

    @Column(name = "size")
    private Integer size;

    @Column(name = "down_count")
    private Integer downCount = 0;

    @Column(name = "description")
    private String description;

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getDownCount() {
        return downCount;
    }

    public void setDownCount(Integer downCount) {
        this.downCount = downCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
