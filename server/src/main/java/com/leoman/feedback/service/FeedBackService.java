package com.leoman.feedback.service;

import com.leoman.common.service.GenericManager;
import com.leoman.feedback.entity.Feedback;
import com.leoman.resources.entity.StillResource;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/7/1 0001.
 */
public interface FeedBackService extends GenericManager<Feedback>{

    public Page<Feedback> page(Integer pageNum, Integer pageSize);

    public List<Feedback> findListNew();
}
