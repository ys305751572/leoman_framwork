package com.leoman.banner.service;

import com.leoman.banner.entity.Banner;
import com.leoman.common.service.GenericManager;
import com.leoman.resources.entity.StillResource;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/6/29 0029.
 */
public interface BannerService extends GenericManager<Banner>{

    public Page<Banner> page(Integer pageNum, Integer pageSize);
}
