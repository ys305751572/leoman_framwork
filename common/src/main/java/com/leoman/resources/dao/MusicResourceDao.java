package com.leoman.resources.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.resources.entity.MusicResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
public interface MusicResourceDao extends IBaseJpaRepository<MusicResource> {

    @Query("select a from MusicResource a where a.category.id = ?1")
    public List<MusicResource> iFindListByCategoryId(Long categoryId);

    @Query("select a from MusicResource a group by a.category.id")
    public Page<MusicResource> iPageListGroupByCategoryId(Pageable pageable);
}
