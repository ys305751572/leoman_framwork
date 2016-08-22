package com.leoman.reportapi.service;

import com.leoman.exception.ParamsErrorException;
import com.leoman.exception.UserNotFindException;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface FeedbackApiService {

    public void feedback(Long userId, String content) throws ParamsErrorException, UserNotFindException;
}
