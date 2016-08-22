package com.leoman.barrage.dao;

import com.leoman.barrage.entity.Barrage;
import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.report.entity.Report;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface BarrageDao extends IBaseJpaRepository<Barrage> {

    @Query("select a from Barrage a where a.workId = ?1 and a.type = ?2")
    public List<Barrage> iFindList(Long workId, Integer type);

    @Query("select a from Barrage a where a.createDate > ?1 and a.type = ?2")
    public List<Barrage> findListNew(Long oldDate, Integer type);
}
