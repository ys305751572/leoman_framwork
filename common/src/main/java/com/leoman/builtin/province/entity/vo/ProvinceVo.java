package com.leoman.builtin.province.entity.vo;

import com.leoman.builtin.city.entity.City;

import java.util.List;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public class ProvinceVo {
    private Long id;

    private String name;

    private List<City> cityList;

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

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }
}
