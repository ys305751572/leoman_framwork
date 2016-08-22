package com.leoman.reportapi.service;

import com.leoman.exception.ParamsErrorException;
import com.leoman.exception.UserNotFindException;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface ReportApiService {

    // 举报帖子
    public void report(Long userId, Long postId, String content, Integer type) throws ParamsErrorException, UserNotFindException;
}
