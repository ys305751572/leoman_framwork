package com.leoman.systemsetting.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.systemsetting.entity.Prize;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface PrizeDao extends IBaseJpaRepository<Prize> {

    @Query("select  a from Prize a where a.coin = ?1")
    public Prize findByCoin(Integer coin);
}
