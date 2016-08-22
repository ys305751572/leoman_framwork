package com.leoman.creatordynamic.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.creatordynamic.entity.CreatorDynamic;
import com.leoman.creatordynamic.entity.Dynamic;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface CreatorDynamicDao extends IBaseJpaRepository<CreatorDynamic> {

    @Query("select a from CreatorDynamic a where a.userInfo.id = ?1")
    public List<CreatorDynamic> findListByCreatorIdPlus(Long creatorId);

    @Query("select a from CreatorDynamic a where a.userInfo.id = ?1 and a.date = ?2")
    public List<CreatorDynamic> findListByCreatorIdAndDate(Long creatorId, String date);

    @Query("select a from CreatorDynamic a where a.date = ?1 order by a.id desc")
    public List<CreatorDynamic> findListByDate(String date);

    @Query("select a from CreatorDynamic a order by a.id desc")
    public List<CreatorDynamic> findListByDatePlus();

    @Query("select a.date from CreatorDynamic a where a.userInfo.id = ?1 group by a.date")
    public List<String> findListByCreatorId(Long creatorId);

    @Query("select a.date from CreatorDynamic a group by a.date order by a.id desc")
    public List<String> findList();
}
