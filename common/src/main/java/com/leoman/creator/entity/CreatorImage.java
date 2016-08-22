package com.leoman.creator.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Entity
@Table(name = "t_user_creator_image")
public class CreatorImage extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Creator creator;

    @Column(name = "type")
    private Integer type;

    @Column(name = "image")
    private String image;

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
