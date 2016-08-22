package com.leoman.barrage.service;

import com.leoman.barrage.entity.Barrage;
import com.leoman.common.service.GenericManager;
import com.leoman.report.entity.Report;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public interface BarrageService extends GenericManager<Barrage>{

    public Page<Barrage> page(Long workId, Integer pageNum, Integer pageSize);

    public Page<Barrage> pageList(String name, String mobile, Integer type, Integer pageNum, Integer pageSize);

    public List<Barrage> findListNew(Integer type);
}
