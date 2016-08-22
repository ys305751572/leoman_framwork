package com.leoman.post.service.impl;

import com.leoman.post.dao.PostBaseDao;
import com.leoman.post.entity.PostBase;
import com.leoman.post.service.PostBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/6/12.
 */
@Service
public class PostBaseServiceImpl implements PostBaseService {

    @Autowired
    private PostBaseDao postBaseDao;

    @Override
    public PostBase getPostInfo(Long postId, Integer type) {
        return postBaseDao.getPostInfo(postId, type);
    }
}
