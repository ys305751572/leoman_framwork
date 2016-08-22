package com.leoman.resources.entity.vo;

import java.util.List;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
public class MusicResourceVo {

    private Long id;

    private String name;

    private Integer size;

    private List<MusicVo> musicList;

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

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<MusicVo> getMusicList() {
        return musicList;
    }

    public void setMusicList(List<MusicVo> musicList) {
        this.musicList = musicList;
    }
}
