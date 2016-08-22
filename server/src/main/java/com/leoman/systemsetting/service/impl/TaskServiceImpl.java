package com.leoman.systemsetting.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.systemsetting.dao.TaskDao;
import com.leoman.systemsetting.entity.Task;
import com.leoman.systemsetting.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/7/1 0001.
 */
@Service
public class TaskServiceImpl extends GenericManagerImpl<Task, TaskDao> implements TaskService{

    @Autowired
    private TaskDao taskDao;

    @Override
    public List<Task> findAll() {
        return taskDao.findAll();
    }
}
