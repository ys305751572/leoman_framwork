package com.leoman.post.service;

import com.leoman.common.service.GenericManager;
import com.leoman.post.entity.PostComment;
import com.leoman.userchange.entity.UserChange;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/7/20 0020.
 */
public interface UserChangeService extends GenericManager<UserChange>{

    public Page<UserChange> page(String mobile, Integer type, Integer status, Integer pageNum, Integer pageSize);
}
