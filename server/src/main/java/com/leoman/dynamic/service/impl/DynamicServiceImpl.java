package com.leoman.dynamic.service.impl;

import com.leoman.common.controller.common.CommonController;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.creatordynamic.dao.DynamicDao;
import com.leoman.creatordynamic.entity.Dynamic;
import com.leoman.dynamic.service.DynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
@Service
public class DynamicServiceImpl extends GenericManagerImpl<Dynamic, DynamicDao> implements DynamicService {

    @Autowired
    private DynamicDao dynamicDao;

    @Override
    public List<Dynamic> findByCreatorId(Long id) {
        return dynamicDao.findByCreatorId(id);
    }

    @Override
    public List<Dynamic> findListNew(Long id) {
        return dynamicDao.findListNew(CommonController.getYesterday().getTime(), CommonController.getToday().getTime(), id);
    }
}
