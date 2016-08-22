package com.leoman.floatwin.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.floatwin.dao.FloatWinDao;
import com.leoman.floatwin.entity.FloatWin;
import com.leoman.floatwin.service.FloatWinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
@Service
public class FloatWinServiceImpl extends GenericManagerImpl<FloatWin, FloatWinDao> implements FloatWinService {

    @Autowired
    private FloatWinDao floatWinDao;

    @Override
    public FloatWin getById(Long id) {
        return floatWinDao.findOne(id);
    }
}
