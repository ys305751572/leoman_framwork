package com.leoman.permissions.admin.dao;

import com.leoman.permissions.admin.entity.Admin;
import com.leoman.common.dao.IBaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 后台管理员DAO
 * Created by yesong on 2016/6/12.
 */
public interface AdminDao extends IBaseJpaRepository<Admin> {

    @Query("select a from Admin a where a.username = ?1")
//    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    Admin findByUsername(String username);
}
