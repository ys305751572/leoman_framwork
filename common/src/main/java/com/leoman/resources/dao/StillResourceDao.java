package com.leoman.resources.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.resources.entity.MusicResource;
import com.leoman.resources.entity.StillResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
public interface StillResourceDao extends IBaseJpaRepository<StillResource> {

    @Query("select a from StillResource a where a.category.id = ?1")
    public List<StillResource> iFindListByCategoryId(Long categoryId);

    @Query("select a from StillResource a group by a.category.id")
    public Page<StillResource> iPageListGroupByCategoryId(Pageable pageable);
}
