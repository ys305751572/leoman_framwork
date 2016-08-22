package com.leoman.creatordynamic.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.creatordynamic.entity.Dynamic;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface DynamicDao extends IBaseJpaRepository<Dynamic> {

    @Query("select a from Dynamic a where a.creator.id = ?1 ")
    public List<Dynamic> findByCreatorId(Long id);

    // 查询今天动态
    @Query("select a from Dynamic a where a.time > ?1 and a.time < ?2 and a.creator.id = ?3")
    public List<Dynamic> findListNew(Long today, Long day, Long id);
}
