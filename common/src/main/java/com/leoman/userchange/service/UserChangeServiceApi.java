package com.leoman.userchange.service;

import com.leoman.userchange.entity.UserChange;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface UserChangeServiceApi {

    public List<UserChange> findList(Long sourceId, Integer type, Long userId);
}
