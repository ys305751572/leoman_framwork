package com.leoman.post.service;

import com.leoman.common.service.GenericManager;
import com.leoman.post.entity.PostImage;

import java.util.List;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
public interface PostImageService extends GenericManager<PostImage>{

    public List<PostImage> findList(Long postId, Integer type);

    public PostImage findById(Long postId);
}
