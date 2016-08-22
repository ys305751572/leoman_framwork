package com.leoman.indexapi.service;

import com.leoman.banner.entity.Banner;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface BannerApiService {

    // 查询首页banner图列表
    public List<Banner> iFindList();
}
