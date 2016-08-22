package com.leoman.sohuapi.entity;

import java.util.List;

/**
 * Created by yesong on 2016/8/15.
 */
public class VideoSubFromSohu {

    private Long count;

    private List<VideoFromSohu> list;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<VideoFromSohu> getList() {
        return list;
    }

    public void setList(List<VideoFromSohu> list) {
        this.list = list;
    }
}
