package com.leoman.privateletter.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.entity.vo.FansVo;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_private_letter")
@Entity
public class PrivateLetter extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo user;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private UserInfo toUser;

    @Column(name = "content")
    private String content;

    @Column(name = "voice")
    private String voice;

    @Column(name = "image")
    private String image;

    @Transient
    private FansVo fromUserInfo;

    @Transient
    private FansVo toUserInfo;

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public UserInfo getToUser() {
        return toUser;
    }

    public void setToUser(UserInfo toUser) {
        this.toUser = toUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public FansVo getFromUserInfo() {
        return fromUserInfo;
    }

    public void setFromUserInfo(FansVo fromUserInfo) {
        this.fromUserInfo = fromUserInfo;
    }

    public FansVo getToUserInfo() {
        return toUserInfo;
    }

    public void setToUserInfo(FansVo toUserInfo) {
        this.toUserInfo = toUserInfo;
    }
}
