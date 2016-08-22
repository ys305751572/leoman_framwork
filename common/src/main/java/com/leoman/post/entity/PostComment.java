package com.leoman.post.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.user.entity.UserInfo;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_post_comment")
@Entity
public class PostComment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostBase postBase;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private UserInfo fromUser;

    @ManyToOne
    @JoinColumn(name = "to_post_comment_id")
    private PostComment toPostComment;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private UserInfo toUser;

    @Column(name = "type")
    private Integer type;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private Integer status;

    public PostBase getPostBase() {
        return postBase;
    }

    public void setPostBase(PostBase postBase) {
        this.postBase = postBase;
    }

    public PostComment getToPostComment() {
        return toPostComment;
    }

    public void setToPostComment(PostComment toPostComment) {
        this.toPostComment = toPostComment;
    }

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
