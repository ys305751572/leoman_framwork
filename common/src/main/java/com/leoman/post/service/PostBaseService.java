package com.leoman.post.service;

import com.leoman.post.entity.PostBase;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface PostBaseService {

    // 获取帖子基本信息
    public PostBase getPostInfo(Long postId, Integer type);
}
