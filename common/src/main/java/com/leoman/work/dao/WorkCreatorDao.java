package com.leoman.work.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.work.entity.WorkCreator;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface WorkCreatorDao extends IBaseJpaRepository<WorkCreator> {

    @Query("select a from WorkCreator a where a.userInfo.id = ?1 ")
    public List<WorkCreator> findByUserId(Long userId);

    @Query("select a from WorkCreator a where a.work.id = ?1 ")
    public WorkCreator findByWorkId(Long workId);

    @Query("select a from WorkCreator a where a.work.id = ?1 ")
    public List<WorkCreator> findWorkId(Long workId);

    @Query("select a from WorkCreator a where a.work.id = ?1 order by a.userInfo.coin desc")
    public List<WorkCreator> findWorkIdDesc(Long workId);
}
