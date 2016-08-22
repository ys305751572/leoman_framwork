package com.leoman.systemsetting.service;

import com.leoman.common.service.GenericManager;
import com.leoman.systemsetting.entity.Task;

import java.util.List;

/**
 * Created by Administrator on 2016/7/1 0001.
 */
public interface TaskService extends GenericManager<Task>{

    public List<Task> findAll();
}
