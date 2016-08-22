package com.leoman.work.entity.vo;

import com.leoman.work.entity.WorkComicImage;

import java.util.List;

/**
 * 作品--漫画详情VO类
 * Created by Administrator on 2016/6/14 0014.
 */
public class WorkComicDetailVo {

    private Long id;

    private String name;

    private Integer series;

    private Integer playNum;

    private Integer barrageNum;

    private List<WorkComicImage> imageList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<WorkComicImage> getImageList() {
        return imageList;
    }

    public void setImageList(List<WorkComicImage> imageList) {
        this.imageList = imageList;
    }
}
