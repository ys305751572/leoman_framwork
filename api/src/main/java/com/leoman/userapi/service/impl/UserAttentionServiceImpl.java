package com.leoman.userapi.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.reourceapi.service.impl.ResourceApiServiceImpl;
import com.leoman.user.dao.UserAttentionDao;
import com.leoman.user.entity.UserAttention;
import com.leoman.userapi.service.UserAttentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
@Service
public class UserAttentionServiceImpl extends GenericManagerImpl<UserAttention, UserAttentionDao> implements UserAttentionService {

    @Autowired
    private UserAttentionDao userAttentionDao;

    @Override
    public List<UserAttention> iFindListByUserId(Long userId) {
        return userAttentionDao.findListByUserId(userId);
    }

    @Override
    public List<UserAttention> iFindFans(Long userId) {
        return userAttentionDao.iFindFans(userId);
    }

    @Override
    public Page<UserAttention> iPageByUserId(Long userId, Integer pageNum, Integer pageSize) {
        int[] info = ResourceApiServiceImpl.changePageParams(pageNum, pageSize);
        return userAttentionDao.pageByUserId(userId, new PageRequest(info[0] - 1, info[1]));
    }

    @Override
    public Page<UserAttention> iPageFans(Long userId, Integer pageNum, Integer pageSize) {
        int[] info = ResourceApiServiceImpl.changePageParams(pageNum, pageSize);
        return userAttentionDao.iPageFans(userId, new PageRequest(info[0] - 1, info[1]));
    }

    @Override
    public UserAttention findAttentionInfo(Long userId, Long sourceId) {
        return userAttentionDao.findOneInfo(userId, sourceId);
    }
}
