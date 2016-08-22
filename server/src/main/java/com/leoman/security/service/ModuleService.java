package com.leoman.security.service;

import com.leoman.common.service.GenericManager;
import com.leoman.role.entity.Modules;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/5/22.
 */
public interface ModuleService extends GenericManager<Modules> {

    public Page<Modules> findPage(Integer pagenum, Integer pagesize);

    //public void saveModule(Modules module, Long parentId);
}
