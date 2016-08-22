package com.leoman.role.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.role.dao.RoleModuleDao;
import com.leoman.role.entity.RoleModule;
import com.leoman.role.service.RoleModulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
@Service
public class RoleModulesServiceImpl extends GenericManagerImpl<RoleModule, RoleModuleDao> implements RoleModulesService {

    @Autowired
    private RoleModuleDao roleModuleDao;


    @Override
    public List<RoleModule> findListByRoleId(Long roleId) {
        return roleModuleDao.findListByRoleId(roleId);
    }

    @Override
    @Transactional
    public void deleteByRoleId(Long roleId) {
        List<RoleModule> list = roleModuleDao.findListByRoleId(roleId);
        for (RoleModule rolemodules : list) {
            roleModuleDao.delete(rolemodules);
        }
    }
}
