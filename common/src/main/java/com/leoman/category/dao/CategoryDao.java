package com.leoman.category.dao;

import com.leoman.category.entity.Category;
import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.resources.entity.MusicResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface CategoryDao extends IBaseJpaRepository<Category> {

    @Query("select a from Category a where a.type = ?1")
    public List<Category> findByType(Integer type);

    @Query("select a from Category a where a.type = ?1 and a.status = 0")
    public List<Category> findType(Integer type);

    @Query("select a from Category a where a.type > 3 and a.status = 0 group by a.type")
    public List<Category> iFindList();

    @Query("select a from MusicResource a where a.category.type = ?1 and a.category.status = 0")
    public Page<MusicResource> iPageByTypeShow(Integer type, Pageable pageable);

    @Query("select a from Category a where a.type > 3 and a.status = 0 group by a.type")
    public Page<Category> iPage(Pageable pageable);
}
