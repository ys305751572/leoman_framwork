package com.leoman.systemsetting.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.systemsetting.dao.LimitWordsDao;
import com.leoman.systemsetting.entity.LimitWords;
import com.leoman.systemsetting.service.LimitWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
@Service
public class LimitWordsServiceImpl extends GenericManagerImpl<LimitWords, LimitWordsDao> implements LimitWordsService {

    @Autowired
    private LimitWordsDao limitWordsDao;

    @Override
    public LimitWords getById(Long id) {
        return limitWordsDao.findOne(id);
    }
}
