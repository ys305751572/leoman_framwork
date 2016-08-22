package com.leoman.reportapi.service.impl;

import com.leoman.exception.ParamsErrorException;
import com.leoman.exception.UserNotFindException;
import com.leoman.feedback.dao.FeedbackDao;
import com.leoman.feedback.entity.Feedback;
import com.leoman.reportapi.service.FeedbackApiService;
import com.leoman.user.dao.UserInfoDao;
import com.leoman.user.entity.UserInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/6/12.
 */
@Service
public class FeedbackApiServiceImpl implements FeedbackApiService {

    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private UserInfoDao userInfoDao;


    @Override
    public void feedback(Long userId, String content) throws ParamsErrorException, UserNotFindException {
        if (null == userId || !StringUtils.isNotEmpty(content)) {
            throw new ParamsErrorException();
        }

        UserInfo userInfo = userInfoDao.findOne(userId);
        if (null == userInfo) {
            throw new UserNotFindException();
        }

        Feedback feedback = new Feedback();
        feedback.setUserInfo(userInfo);
        feedback.setContent(content);

        feedbackDao.save(feedback);
    }
}
