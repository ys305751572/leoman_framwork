package com.leoman.feedback.controller;

import com.leoman.category.entity.Category;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.feedback.service.impl.FeedBackServiceImpl;
import com.leoman.feedback.entity.Feedback;
import com.leoman.feedback.service.FeedBackService;
import com.leoman.report.entity.Report;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/1 0001.
 */
@RequestMapping(value = "admin/feedback")
@Controller
public class FeedbackController extends GenericEntityController<Feedback, Feedback, FeedBackServiceImpl>{

    @Autowired
    private FeedBackService feedBackService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "feedback/list";
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     Integer draw,
                     Integer start,
                     Integer length) {
        try {

            int pageNum = getPageNum(start, length);

            Page<Feedback> page = feedBackService.page(pageNum, length);

            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
            WebUtil.print(response, emptyData);
        }
    }

    /**
     * 批量删除
     *
     * @return
     */
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ResponseBody
    public Integer deleteBatch(String ids) {
        Long[] arrayId = JsonUtil.json2Obj(ids, Long[].class);
        for (Long id : arrayId) {

            Feedback feedback = feedBackService.queryByPK(id);
            feedBackService.delete(feedback);
        }
        return 1;
    }
}
