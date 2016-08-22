package com.leoman.work.entity.vo;

import com.leoman.post.entity.vo.PostImageVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public class WorkCommentVo {

    private Long id;

    private Long userId;

    private String nickname;

    private String avater;

    private Integer level;

    private String content;

    private List<PostImageVo> commentImageList = new ArrayList<PostImageVo>();

    private Integer praise;

    private Long createDate;

    private String replyName;

    private String replyContent;

    private List<PostImageVo> replyImageList = new ArrayList<PostImageVo>();

    // private List<WorkCommentVo> replyList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPraise() {
        return praise;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public List<PostImageVo> getCommentImageList() {
        return commentImageList;
    }

    public void setCommentImageList(List<PostImageVo> commentImageList) {
        this.commentImageList = commentImageList;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public List<PostImageVo> getReplyImageList() {
        return replyImageList;
    }

    public void setReplyImageList(List<PostImageVo> replyImageList) {
        this.replyImageList = replyImageList;
    }

    /*public List<WorkCommentVo> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<WorkCommentVo> replyList) {
        this.replyList = replyList;
    }*/
}