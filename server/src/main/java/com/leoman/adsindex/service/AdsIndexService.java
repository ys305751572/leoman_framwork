package com.leoman.adsindex.service;

import com.leoman.adsindex.entity.AdsIndex;
import com.leoman.banner.entity.Banner;
import com.leoman.common.service.GenericManager;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
public interface AdsIndexService extends GenericManager<AdsIndex>{

    public Page<AdsIndex> page(Integer pageNum, Integer pageSize);
}
