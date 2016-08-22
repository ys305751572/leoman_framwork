package com.leoman.indexapi.service.Impl;

import com.leoman.entity.Configue;
import com.leoman.exception.ParamsErrorException;
import com.leoman.exception.UserNotFindException;
import com.leoman.exception.UserSignErrorException;
import com.leoman.exception.UsernamePasswordException;
import com.leoman.indexapi.service.LoginServiceApi;
import com.leoman.user.dao.UserCollectDao;
import com.leoman.user.dao.UserSignDao;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.entity.UserLogin;
import com.leoman.user.entity.UserSign;
import com.leoman.userapi.service.UserInfoService;
import com.leoman.userapi.service.UserIntegralService;
import com.leoman.userapi.service.UserLoginServiceApi;
import com.leoman.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Administrator on 2016/6/12.
 */
@Service
public class LoginServiceImplApi implements LoginServiceApi {

    @Autowired
    private UserLoginServiceApi userLoginService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserIntegralService userIntegralService;

    @Autowired
    private UserCollectDao userCollectDao;

    @Autowired
    private UserSignDao userSignDao;

    @Override
    public Boolean loginCheck(UserLogin userLogin, String password) throws UsernamePasswordException, UserNotFindException {
        if (userLogin == null) {
            throw new UsernamePasswordException();
        }
        if (!userLogin.getPassword().equals(password)) {
            throw new UserNotFindException();
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public UserInfo login(String username, String password) throws UsernamePasswordException, UserNotFindException {
        UserLogin userLogin = userLoginService.findByUsername(username);
        UserInfo userInfo = null;
        if (loginCheck(userLogin, password)) {
            userInfo = loginInit(userInfoService.findUserLoginById(userLogin.getId()));
        }

        return userInfo;
    }

    @Override
    public UserInfo loginPlus(String openId, Integer type) throws ParamsErrorException, UserNotFindException {
        if (!StringUtils.isNotEmpty(openId) || null == type) {
            throw new ParamsErrorException();
        }

        UserInfo userInfo = null;

        // 类型，1:微信 2:微博
        if (type == 1) {
            userInfo = userInfoService.findOneByWeixin(openId);
        } else {
            userInfo = userInfoService.findOneByWeibo(openId);
        }

        userInfo = loginInit(userInfo);

        return userInfo;
    }

    @Override
    public void signIn(Long userId) throws UserNotFindException, UserSignErrorException {
        String nowDate = DateUtils.dateToString(new Date(), "yyyy-MM-dd");
        UserSign userSign = userSignDao.findOneByUserId(userId);
        UserInfo userInfo = userInfoService.findOne(userId);

        // 如果没有签到记录，则新增一条签到记录
        if (null == userSign) {
            userSign = new UserSign();
            userSign.setUserInfo(userInfo);
            userSign.setSingDate(nowDate);
            userSign.setDays(1);
        } else {
            if (userSign.getSingDate().equals(nowDate)) {
                throw new UserSignErrorException();
            }
            userSign.setSingDate(nowDate);
            userSign.setDays(userSign.getDays() + 1);
        }

        userSignDao.save(userSign);

        // 根据连续签到天数获取对应的经验
        if (userSign.getDays() == 1) {
            userIntegralService.changeIntegral(userInfo, "签到1天获得2经验值", 2);
        } else if (userSign.getDays() == 3) {
            userIntegralService.changeIntegral(userInfo, "连续签到3天获得4经验值", 4);
        } else if (userSign.getDays() == 5) {
            userIntegralService.changeIntegral(userInfo, "连续签到5天获得6经验值", 6);
        } else if (userSign.getDays() == 7) {
            userIntegralService.changeIntegral(userInfo, "连续签到7天获得8经验值", 8);
        } else if (userSign.getDays() == 10) {
            userIntegralService.changeIntegral(userInfo, "连续签到10天获得10经验值", 10);
        } else if (userSign.getDays() == 20) {
            userIntegralService.changeIntegral(userInfo, "连续签到20天获得12经验值", 12);
        } else if (userSign.getDays() == 30) {
            userIntegralService.changeIntegral(userInfo, "连续签到30天获得15经验值", 15);
        } else {
            userIntegralService.changeIntegral(userInfo, "连续签到" + userSign.getDays() + "天获得18经验值", 18);
        }
    }

    public UserInfo loginInit(UserInfo userInfo) {
        // 查询收藏数
        userInfo.setNum(userCollectDao.findListByUserId(userInfo.getId()).size());

        // 修改最后一次登录时间
        UserLogin userLogin = userInfo.getUserLogin();
        userLogin.setLastLoginDate(System.currentTimeMillis());
        userLoginService.update(userLogin);

        userInfo.setAvater(Configue.getUploadUrl() + userInfo.getAvater());

        return userInfo;
    }
}
