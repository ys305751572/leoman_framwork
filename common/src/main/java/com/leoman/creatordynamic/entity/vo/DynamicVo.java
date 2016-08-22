package com.leoman.creatordynamic.entity.vo;

import java.util.List;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public class DynamicVo {

    private String date;

    private List<CreatorDynamicVo> dynamicList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<CreatorDynamicVo> getDynamicList() {
        return dynamicList;
    }

    public void setDynamicList(List<CreatorDynamicVo> dynamicList) {
        this.dynamicList = dynamicList;
    }
}
