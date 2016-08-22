package com.leoman.message.service;

import com.leoman.common.service.GenericManager;
import com.leoman.message.entity.Message;
import com.leoman.resources.entity.StillResource;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public interface MessageService extends GenericManager<Message>{

    public Page<Message> page(Integer pageNum, Integer pageSize);
}
