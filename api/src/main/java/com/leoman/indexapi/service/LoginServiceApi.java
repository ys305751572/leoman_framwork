package com.leoman.indexapi.service;

import com.leoman.exception.ParamsErrorException;
import com.leoman.exception.UserNotFindException;
import com.leoman.exception.UserSignErrorException;
import com.leoman.exception.UsernamePasswordException;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.entity.UserLogin;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface LoginServiceApi {

    public Boolean loginCheck(UserLogin userLogin, String password) throws UsernamePasswordException, UserNotFindException;

    public UserInfo login(String username, String password) throws UsernamePasswordException, UserNotFindException;

    public UserInfo loginPlus(String openId, Integer type) throws ParamsErrorException, UserNotFindException;

    // 用户签到
    public void signIn(Long userId) throws UserNotFindException, UserSignErrorException;
}
