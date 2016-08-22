package com.leoman.work.service;

import com.leoman.common.service.GenericManager;
import com.leoman.work.entity.WorkCreator;

import java.util.List;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
public interface WorkCreatorService extends GenericManager<WorkCreator> {

    public List<WorkCreator> findByUserId(Long userId);

    public WorkCreator findByWorkId(Long workId);

    public List<WorkCreator> findWorkId(Long workId);
}
