package com.leoman.work.entity.vo;

import com.leoman.work.entity.WorkSurround;

import java.util.List;

/**
 * 作品--视频VO类
 * Created by Administrator on 2016/6/14 0014.
 */
public class WorkVideoVo {

    private Long id;

    private String name;

    private String cover;

    private Integer series;

    private String linkUrl;

    private Integer playNum;

    private Integer barrageNum;

    private Integer isEnd;

    private Integer isCollect;

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

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getPlayNum() {
        return playNum;
    }

    public void setPlayNum(Integer playNum) {
        this.playNum = playNum;
    }

    public Integer getBarrageNum() {
        return barrageNum;
    }

    public void setBarrageNum(Integer barrageNum) {
        this.barrageNum = barrageNum;
    }

    public Integer getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(Integer isEnd) {
        this.isEnd = isEnd;
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
