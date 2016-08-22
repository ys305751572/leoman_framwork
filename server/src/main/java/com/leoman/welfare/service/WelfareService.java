package com.leoman.welfare.service;

import com.leoman.common.service.GenericManager;
import com.leoman.resources.entity.GameResource;
import com.leoman.welfare.entity.Welfare;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/6/29 0029.
 */
public interface WelfareService extends GenericManager<Welfare>{

    public Page<Welfare> page(Integer pageNum, Integer pageSize);

    public List<Welfare> findByType(Integer type);
}
