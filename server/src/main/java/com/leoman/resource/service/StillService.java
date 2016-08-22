package com.leoman.resource.service;

import com.leoman.common.service.GenericManager;
import com.leoman.resources.entity.StillResource;
import com.leoman.work.entity.Work;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
public interface StillService extends GenericManager<StillResource>{

    public Page<StillResource> page(Integer pageNum, Integer pageSize);
}
