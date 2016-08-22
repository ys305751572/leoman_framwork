package com.leoman.userapi.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.entity.Constant;
import com.leoman.reourceapi.service.impl.ResourceApiServiceImpl;
import com.leoman.user.dao.UserInfoDao;
import com.leoman.user.dao.UserIntegralDao;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.entity.UserIntegral;
import com.leoman.userapi.service.UserIntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2016/6/12.
 */
@Service
public class UserIntegralServiceImpl extends GenericManagerImpl<UserIntegral, UserIntegralDao> implements UserIntegralService {

    @Autowired
    private UserIntegralDao userIntegralDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    @Transactional
    public void changeIntegral(UserInfo userInfo, String content, Integer count) {
        if (userInfo.getAllIntegral() >= 1000) {
            return;
        }

        UserIntegral userIntegral = new UserIntegral();
        userIntegral.setUserInfo(userInfo);
        userIntegral.setContent(content);
        userIntegral.setCount(count);
        userIntegral.setType(Constant.USER_INTEGRAL_CHANGE_TYPE_001);

        userIntegralDao.save(userIntegral);

        // 更新用户积分数
        userInfo.setIntegral(userInfo.getIntegral() + count);
        userInfo.setAllIntegral(userInfo.getAllIntegral() + count);

        // 更新用户等级
        if (userInfo.getIntegral() < 600) {
            userInfo.setLevel(1);
        } else if (userInfo.getIntegral() < 1200) {
            userInfo.setLevel(2);
        } else if (userInfo.getIntegral() < 2500) {
            userInfo.setLevel(3);
        } else if (userInfo.getIntegral() < 5000) {
            userInfo.setLevel(4);
        } else if (userInfo.getIntegral() < 8000) {
            userInfo.setLevel(5);
        } else if (userInfo.getIntegral() < 14000) {
            userInfo.setLevel(6);
        } else if (userInfo.getIntegral() < 25000) {
            userInfo.setLevel(7);
        } else if (userInfo.getIntegral() < 50000) {
            userInfo.setLevel(8);
        } else if (userInfo.getIntegral() < 80000) {
            userInfo.setLevel(9);
        } else if (userInfo.getIntegral() < 140000) {
            userInfo.setLevel(10);
        } else if (userInfo.getIntegral() < 250000) {
            userInfo.setLevel(11);
        } else if (userInfo.getIntegral() < 400000) {
            userInfo.setLevel(12);
        } else if (userInfo.getIntegral() < 600000) {
            userInfo.setLevel(13);
        } else if (userInfo.getIntegral() < 900000) {
            userInfo.setLevel(14);
        } else if (userInfo.getIntegral() < 1500000) {
            userInfo.setLevel(15);
        } else if (userInfo.getIntegral() < 2000000) {
            userInfo.setLevel(16);
        } else if (userInfo.getIntegral() < 3000000) {
            userInfo.setLevel(17);
        } else {
            userInfo.setLevel(18);
        }

        userInfoDao.save(userInfo);
    }

    @Override
    @Transactional
    public void changeCoin(UserInfo userInfo, String content, Integer count) {
        // 增加馒头变动记录
        UserIntegral userIntegral = new UserIntegral();
        userIntegral.setUserInfo(userInfo);
        userIntegral.setCount(count);
        userIntegral.setType(Constant.USER_INTEGRAL_CHANGE_TYPE_002);
        userIntegral.setContent(content);

        userIntegralDao.save(userIntegral);

        // 修改用户馒头数
        userInfo.setCoin(userInfo.getCoin() + count);
        userInfoDao.save(userInfo);
    }

    @Override
    public Page<UserIntegral> integralPage(Long userId, Integer type, Integer pageNum, Integer pageSize) {
        int[] info = ResourceApiServiceImpl.changePageParams(pageNum, pageSize);
        return userIntegralDao.pageByParams(userId, type, new PageRequest(info[0] - 1, info[1]));
    }
}
