package com.leoman.role.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.role.entity.RoleModule;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public interface RoleModuleDao extends IBaseJpaRepository<RoleModule> {

    @Query("select a from RoleModule a where a.role.id = ?1")
    public List<RoleModule> findListByRoleId(Long roleId);

    @Query("select a from RoleModule a where a.role.id = ?1 group by a.module.parentId")
    public List<RoleModule> findListByRoleIdGroupByParentId(Long roleId);
}
