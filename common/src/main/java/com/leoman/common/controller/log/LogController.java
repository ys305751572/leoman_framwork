package com.leoman.common.controller.log;

import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.common.log.entity.LogEntity;
import com.leoman.common.log.service.LogService;
import com.leoman.common.log.service.impl.LogServiceImpl;
import com.leoman.common.service.Query;
import com.leoman.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.Map;

/**
 * 日志controller
 * Created by yesong on 2016/9/6.
 */
@RequestMapping(value = "/admin/log")
@Controller
public class LogController extends GenericEntityController<LogEntity,LogEntity,LogServiceImpl>{

    @Autowired
    public LogService logService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "system/log/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> list(Integer draw, Integer start, Integer length, String startDate, String endDate,Integer logType) throws Exception{

        int pagenum = getPageNum(start,length);
        Query query = Query.forClass(LogEntity.class,logService);
        query.setPagenum(pagenum);
        query.setPagesize(length);
        if(logType != null) {
            query.eq("logType", logType);
        }
        long _startDate;
        long _endDate;
        // 默认查询当天日志
        try {
            if(StringUtils.isNotBlank(startDate)) {
                startDate = startDate + " 00:00:00";
                _startDate = DateUtils.stringToLong(startDate,"yyyy-MM-dd HH:mm:ss");
                query.ge("createDate",_startDate);
            }
            else {
                _startDate = DateUtils.getTimesmorning();
                query.ge("createDate",_startDate);
            }
            if(StringUtils.isNotBlank(endDate)) {
                endDate = endDate + " 23:59:59";
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
        Page<LogEntity> page = logService.queryPage(query);
        return DataTableFactory.fitting(draw, page);
    }

    /**
     * 查询日志详细信息，
     * 这里主要查询错误日志，错误日志较大，而且多出换行，直接在list页面传入会出错
     * @param id
     * @return
     */
    @RequestMapping(value = "/getMessage", method = RequestMethod.POST)
    @ResponseBody
    public Result getMessage(Long id) {
        try {
            LogEntity logEntity = logService.queryByPK(id);
            return Result.success(logEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }
}
