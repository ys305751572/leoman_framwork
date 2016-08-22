package com.leoman.work.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.work.entity.WorkVideo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface WorkVideoDao extends IBaseJpaRepository<WorkVideo> {

    @Query("select a from WorkVideo a where a.work.id = ?1 order by a.updateDate desc")
    public List<WorkVideo> findByWorkId(Long workId);
}
