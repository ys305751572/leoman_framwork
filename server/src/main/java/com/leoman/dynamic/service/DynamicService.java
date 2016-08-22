package com.leoman.dynamic.service;

import com.leoman.common.service.GenericManager;
import com.leoman.creatordynamic.entity.Dynamic;

import java.util.List;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
public interface DynamicService extends GenericManager<Dynamic> {

    public List<Dynamic> findByCreatorId(Long id);

    // 查询今天动态
    public List<Dynamic> findListNew(Long id);
}
