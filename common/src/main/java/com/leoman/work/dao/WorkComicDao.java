package com.leoman.work.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.work.entity.WorkComic;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface WorkComicDao extends IBaseJpaRepository<WorkComic> {

    @Query("select a from WorkComic a where a.work.id = ?1 order by a.series asc")
    public List<WorkComic> findByWorkId(Long workId);
}
