package com.leoman.post.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.post.entity.vo.PostImageVo;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_post_zb_info")
@Entity
public class ZbPostInfo extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostBase postBase;

    @Column(name = "content")
    private String content;

    @Transient
    private List<PostImage> imageList;

    public PostBase getPostBase() {
        return postBase;
    }

    public void setPostBase(PostBase postBase) {
        this.postBase = postBase;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<PostImage> getImageList() {
        return imageList;
    }

    public void setImageList(List<PostImage> imageList) {
        this.imageList = imageList;
    }
}
