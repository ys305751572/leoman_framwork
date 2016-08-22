package com.leoman.role.service;

import com.leoman.common.service.GenericManager;
import com.leoman.role.entity.Role;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
public interface RoleService extends GenericManager<Role>{

    public Page<Role> page(Integer pageNum, Integer pageSize);

    public Role getById(Long roleId);
}
