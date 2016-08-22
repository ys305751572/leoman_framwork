package com.leoman.userchange.service.impl;

import com.leoman.userchange.dao.UserChangeDao;
import com.leoman.userchange.entity.UserChange;
import com.leoman.userchange.service.UserChangeServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
@Service
public class UserChangeServiceApiImpl implements UserChangeServiceApi {

    @Autowired
    private UserChangeDao userChangeDao;

    @Override
    public List<UserChange> findList(Long sourceId, Integer type, Long userId) {
        return userChangeDao.findList(sourceId, type, userId);
    }
}
