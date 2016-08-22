package com.leoman.work.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.work.entity.WorkNovel;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface WorkNovelDao extends IBaseJpaRepository<WorkNovel> {

    @Query("select a from WorkNovel a where a.work.id = ?1")
    public List<WorkNovel> findByWorkId(Long workId);
}
