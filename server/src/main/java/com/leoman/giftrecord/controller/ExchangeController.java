package com.leoman.giftrecord.controller;

import com.leoman.category.entity.Category;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.giftrecord.entity.GiftExchangeRecord;
import com.leoman.giftrecord.service.ExchangeService;
import com.leoman.giftrecord.service.impl.ExchangeServiceImpl;
import com.leoman.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/5 0005.
 */
@Controller
@RequestMapping(value = "admin/exchange")
public class ExchangeController extends GenericEntityController<GiftExchangeRecord, GiftExchangeRecord, ExchangeServiceImpl>{

    @Autowired
    private ExchangeService exchangeService;

    /**
     * 列表页面
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "giftrecord/list";
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

            Page<GiftExchangeRecord> page = exchangeService.page(pageNum, length);

            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
            WebUtil.print(response, emptyData);
        }
    }

    /**
     * 确认邮寄
     */
    @RequestMapping(value = "/check")
    @ResponseBody
    public Integer check(HttpServletRequest request, Long exchangeId, String courierName, String courierSn) throws UnsupportedEncodingException {

        String msg = request.getParameter("courierName");
        String name = new String(msg.getBytes("ISO-8859-1"), "UTF-8");

        GiftExchangeRecord giftExchangeRecord = exchangeService.queryByPK(exchangeId);
        giftExchangeRecord.setCourierName(name);
        giftExchangeRecord.setCourierSn(courierSn);
        giftExchangeRecord.setStatus(1);

        exchangeService.update(giftExchangeRecord);
        return 1;
    }
}
