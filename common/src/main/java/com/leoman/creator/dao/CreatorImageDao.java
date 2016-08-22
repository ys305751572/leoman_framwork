package com.leoman.creator.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.creator.entity.CreatorImage;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public interface CreatorImageDao extends IBaseJpaRepository<CreatorImage> {

    @Query("select a from CreatorImage a where a.creator.id = ?1 ")
    public List<CreatorImage> findByCreatorId(Long creatorId);

    @Query("select a from CreatorImage a where a.creator.id = ?1 and a.type = ?2")
    public List<CreatorImage> findList(Long creatorId, Integer type);

    @Query("select a from CreatorImage a where a.id = ?1 and a.type = ?2")
    public CreatorImage findOne(Long creatorImageId, Integer type);
}
