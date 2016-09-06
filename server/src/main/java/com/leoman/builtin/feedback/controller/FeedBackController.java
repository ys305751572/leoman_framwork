package com.leoman.builtin.feedback.controller;

import com.leoman.builtin.feedback.entity.Feedback;
import com.leoman.builtin.feedback.service.FeedBackService;
import com.leoman.builtin.feedback.service.impl.FeedBackServiceImpl;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.common.service.Query;
import com.leoman.utils.DateUtils;
import com.leoman.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.Map;

/**
 * 反馈controller
 * Created by yesong on 2016/9/6.
 */
@RequestMapping(value = "/admin/feedback")
@Controller
public class FeedBackController extends GenericEntityController<Feedback, Feedback, FeedBackServiceImpl> {

    @Autowired
    private FeedBackService feedBackService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "system/feedback/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> list(Integer draw, Integer start,Integer length,String startDate,String endDate) {

        int pagenum = getPageNum(start,length);
        Query query = Query.forClass(Feedback.class,feedBackService);

        long _startDate;
        long _endDate;
        // 默认查询当天日志
        try {
            if(StringUtils.isNotBlank(startDate)) {
                _startDate = DateUtils.stringToLong(startDate,"yyyy-MM-dd HH:mm:ss");
                query.ge("createDate",_startDate);
            }
            else {
                _startDate = DateUtils.getTimesmorning();
                query.ge("createDate",_startDate);
            }
            if(StringUtils.isNotBlank(endDate)) {
                _endDate = DateUtils.stringToLong(endDate,"yyyy-MM-dd HH:mm:ss");
                query.le("createDate", _endDate);
            }
            else {
                _endDate = DateUtils.getTimesnight();
                query.le("createDate", _endDate);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        query.setPagenum(pagenum);
        query.setPagesize(length);
        Page<Feedback> page = feedBackService.queryPage(query);
        return DataTableFactory.fitting(draw, page);
    }

    /**
     * 批量删除
     * @param ids 批量删除的id集合，用都好分开
     * @return 操作结果
     */
    @RequestMapping(value = "/delete/batch")
    @ResponseBody
    public Result deleteBatch(String ids) {
        Long[] _ids = JsonUtil.json2Obj(ids,Long[].class);
        try {
            for (Long id : _ids) {
                feedBackService.delete(feedBackService.queryByPK(id));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }
}
