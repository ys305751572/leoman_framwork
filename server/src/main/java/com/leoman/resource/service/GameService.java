package com.leoman.resource.service;

import com.leoman.common.service.GenericManager;
import com.leoman.resources.entity.GameResource;
import com.leoman.resources.entity.StillResource;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
public interface GameService extends GenericManager<GameResource>{

    public Page<GameResource> page(Integer pageNum, Integer pageSize);
}
