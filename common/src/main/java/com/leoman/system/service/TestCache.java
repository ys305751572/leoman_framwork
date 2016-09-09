package com.leoman.system.service;

import com.leoman.system.entity.TestEntity;

/**
 * spring cache 测试
 * Created by yesong on 2016/9/7.
 */
public interface TestCache {

    TestEntity getByName(String username);
}
