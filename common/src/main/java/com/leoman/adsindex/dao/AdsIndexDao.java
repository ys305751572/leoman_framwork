package com.leoman.adsindex.dao;

import com.leoman.adsindex.entity.AdsIndex;
import com.leoman.common.dao.IBaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface AdsIndexDao extends IBaseJpaRepository<AdsIndex> {

    @Query("select a from AdsIndex a where a.position = 1")
    public AdsIndex iFindOne();
}
