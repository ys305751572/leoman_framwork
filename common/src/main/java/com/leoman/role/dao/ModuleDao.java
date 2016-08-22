package com.leoman.role.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.role.entity.Modules;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public interface ModuleDao extends IBaseJpaRepository<Modules> {

    @Query("select a from Modules a where a.parentId = ?1")
    public List<Modules> findListByParentId(Long parentId);

}
