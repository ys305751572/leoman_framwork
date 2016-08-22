package com.leoman.admin.dao;

import com.leoman.admin.entity.AdminRole;
import com.leoman.common.dao.IBaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface AdminRoleDao extends IBaseJpaRepository<AdminRole> {

    @Query("select a from AdminRole a where a.role.id = ?1")
    public List<AdminRole> findByRoleId(Long roleId);

    @Query("select a from AdminRole a where a.admin.username = ?1 and a.admin.password = ?2")
    public AdminRole sysUserLogin(String account, String password);
}
