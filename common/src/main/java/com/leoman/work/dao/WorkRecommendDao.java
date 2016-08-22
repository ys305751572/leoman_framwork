package com.leoman.work.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.work.entity.WorkRecommend;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface WorkRecommendDao extends IBaseJpaRepository<WorkRecommend> {

    @Query("select a from WorkRecommend a where a.work.id = ?1 and a.type = ?2 order by a.id desc")
    public List<WorkRecommend> findListByParams(Long workId, Integer type);
}
