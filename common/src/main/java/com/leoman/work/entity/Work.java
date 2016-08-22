package com.leoman.work.entity;

import com.leoman.category.entity.Category;
import com.leoman.entity.BaseEntity;
import com.leoman.sohuapi.entity.AlbumFromSohu;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_works")
@Entity
public class Work extends BaseEntity {

    @Column(name = "update_count")
    private Integer updateCount;

    @Column(name = "sohu_id")
    private Long albumId;

    @Column(name = "name")
    private String name;

    @Column(name = "series_count")
    private Integer seriesCount;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @Column(name = "description")
    private String description;

    @Column(name = "is_end")
    private Integer isEnd = 0;

    @Column(name = "cover")
    private String cover;

    @Column(name = "barragenum")
    private Integer barrageNum = 0;

    @Column(name = "playnum")
    private Integer playNum = 0;

    @Transient
    private WorkVideo workVideo = new WorkVideo();

    @Transient
    private WorkNovel workNovel = new WorkNovel();

    @Transient
    private WorkComic workComic = new WorkComic();

    @Transient
    private Integer isBarrage;

    @Transient
    private Long creatorId;

    @Transient
    private Integer position;

    @Transient
    private Long workId;

    public Integer getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(Integer updateCount) {
        this.updateCount = updateCount;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeriesCount() {
        return seriesCount;
    }

    public void setSeriesCount(Integer seriesCount) {
        this.seriesCount = seriesCount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(Integer isEnd) {
        this.isEnd = isEnd;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getBarrageNum() {
        return barrageNum;
    }

    public void setBarrageNum(Integer barrageNum) {
        this.barrageNum = barrageNum;
    }

    public WorkVideo getWorkVideo() {
        return workVideo;
    }

    public void setWorkVideo(WorkVideo workVideo) {
        this.workVideo = workVideo;
    }

    public Integer getIsBarrage() {
        return isBarrage;
    }

    public void setIsBarrage(Integer isBarrage) {
        this.isBarrage = isBarrage;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public Integer getPlayNum() {
        return playNum;
    }

    public void setPlayNum(Integer playNum) {
        this.playNum = playNum;
    }

    public WorkNovel getWorkNovel() {
        return workNovel;
    }

    public void setWorkNovel(WorkNovel workNovel) {
        this.workNovel = workNovel;
    }

    public WorkComic getWorkComic() {
        return workComic;
    }

    public void setWorkComic(WorkComic workComic) {
        this.workComic = workComic;
    }
}
