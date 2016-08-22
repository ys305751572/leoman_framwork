package com.leoman.role.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.role.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public interface RoleDao extends IBaseJpaRepository<Role> {

    @Query("select a from Role a where a.id <> 1")
    public Page<Role> page(Pageable pageable);
}
