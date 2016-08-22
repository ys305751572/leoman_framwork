package com.leoman.work.service;

import com.leoman.common.service.GenericManager;
import com.leoman.work.entity.Work;
import com.leoman.work.entity.WorkVideo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/6/16 0016.
 */
public interface WorkVideoService extends GenericManager<WorkVideo>{

    public List<WorkVideo> findByWorkId(Long workId);

    public Page<WorkVideo> page(Long workId, Integer pageNum, Integer pageSize);
}
