package com.leoman.adsindex.entity.vo;

import com.leoman.entity.BaseEntity;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public class AdsIndexVo extends BaseEntity {

    /**
     * 大标题
     */
    private String title;

    /**
     * 小标题
     */
    private String subTitle;

    /**
     * 封面
     */
    private String cover;

    /**
     * 类型：0:帖子 1:视频 2:小说 3:漫画 4:资源
     */
    private Integer position;

    /**
     * 关联id
     */
    private Long workId;

    /**
     * 主创弹幕数（主创回复数）
     */
    private Integer creatorBarrageNum;

    /**
     * 粉丝弹幕数（粉丝回复数）
     */
    private Integer barrageNum;

    /**
     * 作者名称
     */
    private String authorName;

    /**
     * 作者头像
     */
    private String authorHead;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    public Integer getCreatorBarrageNum() {
        return creatorBarrageNum;
    }

    public void setCreatorBarrageNum(Integer creatorBarrageNum) {
        this.creatorBarrageNum = creatorBarrageNum;
    }

    public Integer getBarrageNum() {
        return barrageNum;
    }

    public void setBarrageNum(Integer barrageNum) {
        this.barrageNum = barrageNum;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorHead() {
        return authorHead;
    }

    public void setAuthorHead(String authorHead) {
        this.authorHead = authorHead;
    }
}
