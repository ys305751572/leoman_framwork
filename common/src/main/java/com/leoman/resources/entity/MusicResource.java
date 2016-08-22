package com.leoman.resources.entity;

import com.leoman.category.entity.Category;
import com.leoman.entity.BaseEntity;
import com.leoman.resources.entity.vo.MusicVo;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
@Entity
@Table(name = "t_resource_music")
public class MusicResource extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "singer")
    private String singer;

    @Column(name = "url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @Column(name = "num")
    private Integer num = 0;

    @Transient
    private Integer size;

    @Transient
    private List<MusicVo> musicList;

    @Transient
    private String cover;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

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

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<MusicVo> getMusicList() {
        return musicList;
    }

    public void setMusicList(List<MusicVo> musicList) {
        this.musicList = musicList;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
