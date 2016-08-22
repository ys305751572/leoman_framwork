package com.leoman.sensitivewords.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.sensitivewords.dao.SensitiveWordsDao;
import com.leoman.sensitivewords.entity.SensitiveWords;
import com.leoman.sensitivewords.service.SensitiveWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
@Service
public class SensitiveWordsServiceImpl extends GenericManagerImpl<SensitiveWords, SensitiveWordsDao> implements SensitiveWordsService {

    @Autowired
    private SensitiveWordsDao sensitiveWordsDao;

    @Override
    public List<SensitiveWords> findByCode(String code) {
        return sensitiveWordsDao.findByCode(code);
    }
}
