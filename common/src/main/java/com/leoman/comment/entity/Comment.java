package com.leoman.comment.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.user.entity.UserInfo;
import com.leoman.work.entity.WorkComic;
import com.leoman.work.entity.WorkNovel;
import com.leoman.work.entity.WorkVideo;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_comment")
@Entity
public class Comment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private UserInfo fromUser;

    @Column(name = "works_id")
    private Long worksId;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private UserInfo toUser;

    @Column(name = "praise")
    private Long praise;

    @Column(name = "content")
    private String content;

    @Column(name = "type")
    private Integer type;

    @Transient
    private UserInfo userInfo;

    @Transient
    private WorkVideo workVideo;

    @Transient
    private WorkComic workComic;

    @Transient
    private WorkNovel workNovel;

    public UserInfo getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserInfo fromUser) {
        this.fromUser = fromUser;
    }

    public UserInfo getToUser() {
        return toUser;
    }

    public void setToUser(UserInfo toUser) {
        this.toUser = toUser;
    }

    public Long getWorksId() {
        return worksId;
    }

    public void setWorksId(Long worksId) {
        this.worksId = worksId;
    }

    public Long getPraise() {
        return praise;
    }

    public void setPraise(Long praise) {
        this.praise = praise;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public WorkVideo getWorkVideo() {
        return workVideo;
    }

    public void setWorkVideo(WorkVideo workVideo) {
        this.workVideo = workVideo;
    }

    public WorkComic getWorkComic() {
        return workComic;
    }

    public void setWorkComic(WorkComic workComic) {
        this.workComic = workComic;
    }

    public WorkNovel getWorkNovel() {
        return workNovel;
    }

    public void setWorkNovel(WorkNovel workNovel) {
        this.workNovel = workNovel;
    }
}
