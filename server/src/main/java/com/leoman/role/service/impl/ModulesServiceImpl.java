package com.leoman.role.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.role.dao.ModuleDao;
import com.leoman.role.dao.RoleModuleDao;
import com.leoman.role.entity.Modules;
import com.leoman.role.entity.RoleModule;
import com.leoman.role.service.ModulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
@Service
public class ModulesServiceImpl extends GenericManagerImpl<Modules, ModuleDao> implements ModulesService {

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private RoleModuleDao roleModuleDao;

    @Override
    public List<Modules> findListByParentId(Long parentId) {
        return moduleDao.findListByParentId(parentId);
    }

    @Override
    public List<Modules> findFirstList(Long roleId) {
        List<Modules> list = new ArrayList<Modules>();

        List<RoleModule> rolemodulesList = roleModuleDao.findListByRoleIdGroupByParentId(roleId);

        for (RoleModule roleModule : rolemodulesList) {
            list.add(moduleDao.findOne(roleModule.getModule().getParentId()));
        }

        return list;
    }

}
