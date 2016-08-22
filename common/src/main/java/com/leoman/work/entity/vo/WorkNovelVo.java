package com.leoman.work.entity.vo;

import com.leoman.work.entity.WorkSurround;

import java.util.List;

/**
 * 作品--小说VO类
 * Created by Administrator on 2016/6/14 0014.
 */
public class WorkNovelVo {

    private Long id;

    private String name;

    private String cover;

    private String author;

    private Integer series;

    private Integer playNum;

    private String description;

    private Long updateTime;

    private Integer isEnd;

    private Integer isCollect;

    private List<WorkNovelDetailVo> detailList;

    private List<WorkCreatorVo> creatorList;

    private List<WorkSurround> surroundList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public Integer getPlayNum() {
        return playNum;
    }

    public void setPlayNum(Integer playNum) {
        this.playNum = playNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(Integer isEnd) {
        this.isEnd = isEnd;
    }

    public List<WorkNovelDetailVo> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<WorkNovelDetailVo> detailList) {
        this.detailList = detailList;
    }

    public Integer getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Integer isCollect) {
        this.isCollect = isCollect;
    }

    public List<WorkCreatorVo> getCreatorList() {
        return creatorList;
    }

    public void setCreatorList(List<WorkCreatorVo> creatorList) {
        this.creatorList = creatorList;
    }

    public List<WorkSurround> getSurroundList() {
        return surroundList;
    }

    public void setSurroundList(List<WorkSurround> surroundList) {
        this.surroundList = surroundList;
    }
}
