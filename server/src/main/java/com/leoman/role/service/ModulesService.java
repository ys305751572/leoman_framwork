package com.leoman.role.service;

import com.leoman.common.service.GenericManager;
import com.leoman.role.entity.Modules;

import java.util.List;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
public interface ModulesService extends GenericManager<Modules>{

    // 根据权限模块父级id查询所对应的权限列表
    public List<Modules> findListByParentId(Long parentId);

    // 根据角色id查询对应的一级菜单列表
    public List<Modules> findFirstList(Long roleId);
}
