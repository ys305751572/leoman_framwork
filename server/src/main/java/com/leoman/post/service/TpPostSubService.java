package com.leoman.post.service;

import com.leoman.common.service.GenericManager;
import com.leoman.post.entity.TpPostSub;
import com.leoman.post.entity.ZbPostInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/7/13 0013.
 */
public interface TpPostSubService extends GenericManager<TpPostSub>{

    public List<TpPostSub> findByPostId(Long postId);
}
