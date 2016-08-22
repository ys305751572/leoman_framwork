package com.leoman.floatwin.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.floatwin.entity.FloatWin;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface FloatWinDao extends IBaseJpaRepository<FloatWin> {

    @Query("select a from FloatWin a where a.status = 0")
    public FloatWin findOneInfo();
}
