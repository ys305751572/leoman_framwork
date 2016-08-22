package com.leoman.work.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.work.entity.WorkComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface WorkCommentDao extends IBaseJpaRepository<WorkComment> {

    @Query("select a from WorkComment a where a.work.id = ?1 and a.type = ?2 and a.typePlus = ?3")
    public List<WorkComment> iFindList(Long workId, Integer type, Integer status);

    @Query("select a from WorkComment a where a.work.id = ?1 and a.type = ?2")
    public List<WorkComment> iFindList(Long workId, Integer type);

    @Query("select a from WorkComment a where a.work.id = ?1 and a.type = ?2")
    public Page<WorkComment> iPage(Long workId, Integer type, Pageable pageable);

    @Query("select a from WorkComment a where a.toUserId = ?1 and a.typePlus = 1")
    public List<WorkComment> iFindList(Long workCommentId);

    @Query("select a from WorkComment a where a.toUserId = ?1 and a.typePlus = 1")
    public List<WorkComment> iFindListByUserId(Long userId);

    @Query("select a from WorkComment a where a.toUserId = ?1 and a.type = ?2 and a.typePlus = 1")
    public Page<WorkComment> iPageByUserId(Long userId, Integer type, Pageable pageable);

    @Query("select a from WorkComment a where a.work.id = ?1 and a.typePlus = ?2 and a.userInfo.isCreator = ?3 and a.type = ?4")
    public List<WorkComment> findListByParams(Long workId, Integer typePlus, Integer isCreator, Integer type);
}
