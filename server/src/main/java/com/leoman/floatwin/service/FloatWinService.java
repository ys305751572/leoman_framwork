package com.leoman.floatwin.service;

import com.leoman.common.service.GenericManager;
import com.leoman.floatwin.entity.FloatWin;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
public interface FloatWinService extends GenericManager<FloatWin>{

    public FloatWin getById(Long id);
}
