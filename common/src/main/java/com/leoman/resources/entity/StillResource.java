package com.leoman.resources.entity;

import com.leoman.category.entity.Category;
import com.leoman.entity.BaseEntity;
import com.leoman.post.entity.vo.PostImageVo;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
@Entity
@Table(name = "t_resource_still")
public class StillResource extends BaseEntity {

    @Column(name = "url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @Column(name = "num")
    private Integer num = 0;

    @Transient
    private String name;

    @Transient
    private List<PostImageVo> imageList;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PostImageVo> getImageList() {
        return imageList;
    }

    public void setImageList(List<PostImageVo> imageList) {
        this.imageList = imageList;
    }
}
