package com.leoman.report.controller;

import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.post.entity.PostBase;
import com.leoman.post.service.PostBaseService;
import com.leoman.report.entity.Report;
import com.leoman.report.service.ReportService;
import com.leoman.report.service.impl.ReportServiceImpl;
import com.leoman.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/1 0001.
 */
@Controller
@RequestMapping(value = "admin/report")
public class ReportController extends GenericEntityController<Report, Report, ReportServiceImpl> {

    @Autowired
    private ReportService reportService;

    @Autowired
    private PostBaseService postBaseService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "report/list";
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     String nickname1,
                     String mobile,
                     String nickname2,
                     Integer draw,
                     Integer start,
                     Integer length) {
        try {

            int pageNum = getPageNum(start, length);

            Page<Report> page = reportService.page(nickname1, mobile, nickname2, pageNum, length);

            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
            WebUtil.print(response, emptyData);
        }
    }

    /**
     * 删帖
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    private Integer delete(Long reportId) {

        Report report = reportService.queryByPK(reportId);
        postBaseService.delete(report.getPostBase());
        reportService.delete(report);

        return 1;
    }

    /**
     * 锁帖
     */
    @RequestMapping(value = "/lock")
    @ResponseBody
    private Integer lock(Long reportId) {

        Report report = reportService.queryByPK(reportId);
        report.setStatus(2);
        reportService.update(report);

        PostBase postBase = postBaseService.getPostInfo(report.getPostBase().getId(), 3);
        postBase.setStatus(1);
        postBaseService.update(postBase);

        return 1;
    }

    /**
     * 忽略
     */
    @RequestMapping(value = "/ignore")
    @ResponseBody
    private Integer ignore(Long reportId) {

        Report report = reportService.queryByPK(reportId);
        report.setStatus(3);
        reportService.update(report);

        return 1;
    }
}
