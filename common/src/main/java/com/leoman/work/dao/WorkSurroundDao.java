package com.leoman.work.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.work.entity.WorkSurround;
import com.leoman.work.entity.WorkVideo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface WorkSurroundDao extends IBaseJpaRepository<WorkSurround> {

    @Query("select a from WorkSurround a where a.work.id = ?1")
    public List<WorkSurround> findByWorkId(Long workId);
}
