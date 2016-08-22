package com.leoman.work.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.work.dao.WorkCreatorDao;
import com.leoman.work.entity.WorkCreator;
import com.leoman.work.service.WorkCreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
@Service
public class WorkCreatorServiceImpl extends GenericManagerImpl<WorkCreator, WorkCreatorDao> implements WorkCreatorService {

    @Autowired
    private WorkCreatorDao workCreatorDao;

    @Override
    public List<WorkCreator> findByUserId(Long userId) {
        return workCreatorDao.findByUserId(userId);
    }

    @Override
    public WorkCreator findByWorkId(Long workId) {
        return workCreatorDao.findByWorkId(workId);
    }

    @Override
    public List<WorkCreator> findWorkId(Long workId) {
        return workCreatorDao.findWorkId(workId);
    }
}
