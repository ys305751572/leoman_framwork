package com.leoman.userapi.service;

import com.leoman.common.service.GenericManager;
import com.leoman.user.entity.UserAttention;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface UserAttentionService extends GenericManager<UserAttention> {

    public List<UserAttention> iFindListByUserId(Long userId);

    public List<UserAttention> iFindFans(Long userId);

    public Page<UserAttention> iPageByUserId(Long userId, Integer pageNum, Integer pageSize);

    public Page<UserAttention> iPageFans(Long userId, Integer pageNum, Integer pageSize);

    public UserAttention findAttentionInfo(Long userId, Long sourceId);
}
