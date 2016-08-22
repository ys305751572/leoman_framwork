package com.leoman.role.service;

import com.leoman.common.service.GenericManager;
import com.leoman.role.entity.RoleModule;

import java.util.List;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
public interface RoleModulesService extends GenericManager<RoleModule>{

    // 根据角色id查询角色对应的权限列表
    public List<RoleModule> findListByRoleId(Long roleId);

    // 根据角色id删除角色对应的权限
    public void deleteByRoleId(Long roleId);
}
