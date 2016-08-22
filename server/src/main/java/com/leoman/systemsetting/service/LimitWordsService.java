package com.leoman.systemsetting.service;

import com.leoman.common.service.GenericManager;
import com.leoman.systemsetting.entity.LimitWords;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public interface LimitWordsService extends GenericManager<LimitWords> {

    public LimitWords getById(Long id);
}
