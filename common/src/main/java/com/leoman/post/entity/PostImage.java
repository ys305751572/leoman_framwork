package com.leoman.post.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_post_image")
@Entity
public class PostImage extends BaseEntity {

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "type")
    private Integer type;

    @Column(name = "url")
    private String url;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
