package com.leoman.post.service;

import com.leoman.common.service.GenericManager;
import com.leoman.post.entity.ZbPostInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/7/13 0013.
 */
public interface ZbPostInfoService extends GenericManager<ZbPostInfo> {

    public List<ZbPostInfo> findByPostId(Long postId);
}
