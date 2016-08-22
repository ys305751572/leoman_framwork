package com.leoman.dynamic.service;

import com.leoman.common.service.GenericManager;
import com.leoman.creatordynamic.entity.CreatorDynamic;
import com.leoman.resources.entity.StillResource;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/6/30 0030.
 */
public interface CreatorDynamicService extends GenericManager<CreatorDynamic>{

    public Page<CreatorDynamic> page(Integer pageNum, Integer pageSize);
}
