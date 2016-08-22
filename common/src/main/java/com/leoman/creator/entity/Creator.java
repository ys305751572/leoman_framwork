package com.leoman.creator.entity;

import com.leoman.city.entity.City;
import com.leoman.entity.BaseEntity;
import com.leoman.province.entity.Province;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Entity
@Table(name = "t_user_creator")
public class Creator extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "provice_id")
    private Province province;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "description")
    private String description;

    @Column(name = "experience")
    private String experience;

    @Column(name = "weibo")
    private String weibo;

    @Column(name = "audio_url")
    private String audioUrl;

    @Column(name = "cover_url")
    private String coverUrl;

    @Column(name = "status")
    private Integer status = 0;

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
