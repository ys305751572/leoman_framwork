package com.leoman.post.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.post.dao.TpPostSubDao;
import com.leoman.post.entity.TpPostSub;
import com.leoman.post.entity.ZbPostInfo;
import com.leoman.post.service.TpPostSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/7/13 0013.
 */
@Service
public class TpPostSubServiceImpl extends GenericManagerImpl<TpPostSub, TpPostSubDao> implements TpPostSubService{

    @Autowired
    private TpPostSubDao tpPostSubDao;

    @Override
    public List<TpPostSub> findByPostId(Long postId) {
        return tpPostSubDao.getPostInfo(postId);
    }
}
