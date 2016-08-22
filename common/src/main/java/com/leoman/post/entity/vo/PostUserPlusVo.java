package com.leoman.post.entity.vo;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public class PostUserPlusVo {

    private Long id;

    private String nickname;

    private Integer level;

    private String avater;

    private Integer isAttention;

    private Integer isCreator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public Integer getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(Integer isAttention) {
        this.isAttention = isAttention;
    }

    public Integer getIsCreator() {
        return isCreator;
    }

    public void setIsCreator(Integer isCreator) {
        this.isCreator = isCreator;
    }
}
