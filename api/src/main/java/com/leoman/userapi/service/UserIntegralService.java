package com.leoman.userapi.service;

import com.leoman.common.service.GenericManager;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.entity.UserIntegral;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface UserIntegralService extends GenericManager<UserIntegral> {

    // 用户获取经验
    public void changeIntegral(UserInfo userInfo, String content, Integer count);

    // 用户获取馒头
    public void changeCoin(UserInfo userInfo, String content, Integer count);

    // 收支明细记录表
    public Page<UserIntegral> integralPage(Long userId, Integer type, Integer pageNum, Integer pageSize);
}
