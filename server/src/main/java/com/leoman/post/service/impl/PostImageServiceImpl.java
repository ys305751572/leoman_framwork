package com.leoman.post.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.post.dao.PostImageDao;
import com.leoman.post.entity.PostImage;
import com.leoman.post.service.PostImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
@Service
public class PostImageServiceImpl extends GenericManagerImpl<PostImage, PostImageDao> implements PostImageService {

    @Autowired
    private PostImageDao postImageDao;
    @Override
    public List<PostImage> findList(Long postId, Integer type) {
        return postImageDao.findList(postId, type);
    }

    @Override
    public PostImage findById(Long postId) {
        return postImageDao.findById(postId);
    }
}
