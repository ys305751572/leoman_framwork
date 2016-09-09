package com.leoman.system.service.impl;

import com.leoman.system.entity.TestEntity;
import com.leoman.system.service.TestCache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * spring cache 测试
 * Created by yesong on 2016/9/7.
 */
@Service
public class TestCacheImpl implements TestCache{

    @Cacheable(value = "serviceCache")
    @Override
    public TestEntity getByName(String username) {
        // 方法内部实现不考虑缓存逻辑，直接实现业务
        System.out.println("real query account."+ username);
        System.out.println("real querying db..."+ username);
        new TestEntity(username);
        return null;
    }
}
