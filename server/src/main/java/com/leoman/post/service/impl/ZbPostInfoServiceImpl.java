package com.leoman.post.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.post.dao.ZbPostInfoDao;
import com.leoman.post.entity.ZbPostInfo;
import com.leoman.post.service.ZbPostInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/7/13 0013.
 */
@Service
public class ZbPostInfoServiceImpl extends GenericManagerImpl<ZbPostInfo, ZbPostInfoDao> implements ZbPostInfoService {

    @Autowired
    private ZbPostInfoDao zbPostInfoDao;

    @Override
    public List<ZbPostInfo> findByPostId(Long postId) {
        return zbPostInfoDao.getPostInfo(postId);
    }
}
