package com.leoman.builtin.feedback.service.impl;

import com.leoman.builtin.feedback.dao.FeedBackDao;
import com.leoman.builtin.feedback.entity.Feedback;
import com.leoman.builtin.feedback.service.FeedBackService;
import com.leoman.common.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 反馈service impl
 * Created by yesong on 2016/9/6.
 */
@Service
public class FeedBackServiceImpl extends GenericManagerImpl<Feedback, FeedBackDao> implements FeedBackService{

    @Autowired
    private FeedBackDao dao;
}
