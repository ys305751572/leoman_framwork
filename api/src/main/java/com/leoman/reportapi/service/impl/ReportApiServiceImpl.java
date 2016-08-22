package com.leoman.reportapi.service.impl;

import com.leoman.entity.Constant;
import com.leoman.exception.ParamsErrorException;
import com.leoman.exception.UserNotFindException;
import com.leoman.post.dao.PostBaseDao;
import com.leoman.report.dao.ReportDao;
import com.leoman.report.entity.Report;
import com.leoman.reportapi.service.ReportApiService;
import com.leoman.user.dao.UserInfoDao;
import com.leoman.userapi.service.impl.UserLoginServiceApiImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
@Service
public class ReportApiServiceImpl implements ReportApiService {

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private PostBaseDao postBaseDao;

    @Override
    public void report(Long userId, Long postId, String content, Integer type) throws ParamsErrorException, UserNotFindException {
        if (null == userId || null == postId || !StringUtils.isNotEmpty(content) || null == type) {
            throw new ParamsErrorException();
        }

        UserLoginServiceApiImpl.getUserInfo(userId, userInfoDao);

        List<Report> reportList = reportDao.findListByParams(postId);
        // 条件判断：
        // 1、当该帖没有被举报过时，直接新增一条举报信息
        // 2、当该帖已经被举报过时，判断是否已经处理过，如果已经处理，则新增一条已处理的举报记录
        Report report = new Report();
        report.setUserInfo(userInfoDao.findOne(userId));
        report.setPostBase(postBaseDao.findOne(postId));
        report.setReportType(content);
        if (null == reportList || reportList.size() == 0) {
            report.setStatus(Constant.REPORT_STATUS_000);
        } else {
            report.setStatus(reportList.get(0).getStatus());
        }
        reportDao.save(report);
    }
}
