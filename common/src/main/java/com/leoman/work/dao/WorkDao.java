package com.leoman.work.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.work.entity.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface WorkDao extends IBaseJpaRepository<Work> {

    @Query("select a from Work a where a.id in (select b.work.id from WorkRecommend b where a.category.type = b.type)")
    public List<Work> iFindList();

    @Query("select a from Work a where a.createDate >= ?1 and a.createDate <= ?2 and a.id in (select b.id from WorkRecommend b where a.category.type = b.type)")
    public List<Work> iFindList(Long startDate, Long endDate);

    @Query("select a from Work a where a.category.id = ?1 order by a.createDate desc")
    public List<Work> iFindList(Long category);

    @Query("select a from Work a order by a.createDate desc")
    public List<Work> iFindListAll();

    @Query("select a from Work a where a.category.id = ?1 order by a.playNum desc")
    public List<Work> iFindListHot(Long category);

    @Query("select a from Work a order by a.playNum desc")
    public List<Work> iFindListHotAll();

    @Query("select a from Work a where a.category.type = ?1")
    public List<Work> findByType(Integer type);

    @Query("select a from Work a where a.id in (select b.sourceId from UserCollect b where b.userInfo.id = ?1 and b.type = ?3) and a.category.type = ?2")
    public Page<Work> findCollectListByUserIdAndType(Long userId, Integer categoryType, Integer type, Pageable pageable);

    @Query("select a from Work a where a.category.type = ?1 order by a.createDate desc")
    public Page<Work> findCollectListByType(Integer category, Pageable pageable);

    @Query("select a from Work a where a.category.type = ?1 order by a.playNum desc")
    public Page<Work> findCollectListByType2(Integer category, Pageable pageable);

    @Query("select a from Work a where a.albumId = ?1")
    public Work findBySoHuId(Long sohuId);
}
