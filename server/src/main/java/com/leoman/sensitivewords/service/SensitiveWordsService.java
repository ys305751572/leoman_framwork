package com.leoman.sensitivewords.service;

import com.leoman.common.service.GenericManager;
import com.leoman.sensitivewords.entity.SensitiveWords;

import java.util.List;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public interface SensitiveWordsService extends GenericManager<SensitiveWords>{

    public List<SensitiveWords> findByCode(String code);
}
