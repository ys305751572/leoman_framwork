package com.leoman.reportapi.controller;

import com.leoman.common.core.ErrorType;
import com.leoman.exception.ParamsErrorException;
import com.leoman.exception.UserNotFindException;
import com.leoman.reportapi.service.FeedbackApiService;
import com.leoman.reportapi.service.ReportApiService;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.Result;
import com.leoman.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/6/12.
 */
@Controller
@RequestMapping(value = "/app/report")
public class ReportApiController {

    @Autowired
    private ReportApiService reportApiService;

    @Autowired
    private FeedbackApiService feedbackApiService;

    /**
     * @api {post} /app/report/report 1、举报
     * @apiVersion 0.0.1
     * @apiName report.report
     * @apiGroup report
     * @apiDescription 举报
     *
     * @apiParam {Long}      userId               举报人id
     * @apiParam {Long}      postId               帖子id
     * @apiParam {String}    content              举报类型
     * @apiParam {Integer}   type                 分类(0:普通贴 1:直播贴 2:投票贴 )
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/report", method = RequestMethod.POST)
    public void report(HttpServletResponse response, Long userId, Long postId, String content, Integer type) {
        String result = "";
        try {
            reportApiService.report(userId, postId, content, type);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0003));
        }

        WebUtil.printApi(response, result);
    }

    /**
     * @api {post} /app/report/feedback 2、意见反馈
     * @apiVersion 0.0.1
     * @apiName report.feedback
     * @apiGroup report
     * @apiDescription 意见反馈
     *
     * @apiParam {Long}      userId               反馈人id
     * @apiParam {String}    content              反馈内容
     *
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "status":0,
     *      "msg":"",
     *      "data":{}
     * }
     */
    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public void feedback(HttpServletResponse response, Long userId, String content) {
        String result = "";
        try {
            feedbackApiService.feedback(userId, content);
            result = JsonUtil.obj2ApiJson(Result.successApi());
        } catch (ParamsErrorException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0001));
        } catch (UserNotFindException e) {
            result = JsonUtil.obj2ApiJson(Result.failureApi(ErrorType.ERROR_CODE_0003));
        }

        WebUtil.printApi(response, result);
    }
}
