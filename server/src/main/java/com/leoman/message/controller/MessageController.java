package com.leoman.message.controller;

import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.message.entity.Message;
import com.leoman.message.service.MessageService;
import com.leoman.message.service.impl.MessageServiceImpl;
import com.leoman.utils.ConfigUtil;
import com.leoman.utils.DateUtils;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
@Controller
@RequestMapping(value = "admin/message")
public class MessageController extends GenericEntityController<Message, Message, MessageServiceImpl>{

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "message/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public void list(HttpServletResponse response,
                     Integer draw,
                     Integer start,
                     Integer length) {
        int pageNum = getPageNum(start, length);

        Page<Message> page = messageService.page(pageNum, length);

        Map<String, Object> result = DataTableFactory.fitting(draw, page);
        WebUtil.printJson(response, result);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(ModelMap model, Long id) {
        // 如果id不为空，则代表编辑
        if (null != id) {
            Message message = messageService.queryByPK(id);
            message.setContent(message.getContent().replaceAll("&lt", "<").replaceAll("&gt", ">"));
            model.addAttribute("message", message);
        }

        return "message/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Integer save(HttpServletRequest request,
                        Long id,
                        String title,
                        String types,
                        String content,
                        String time) {
        try {
            Message message = null;
            if (id == null) {
                message = new Message();
                message.setCreateDate(System.currentTimeMillis());
            } else {
                message = messageService.queryByPK(id);
                message.setUpdateDate(System.currentTimeMillis());
                messageService.update(message);
            }

            message.setTitle(title);
            message.setToObject(types.substring(0, types.length() - 1));
            message.setContent(content);

            // url
            String url = ConfigUtil.getString("base.url");


            if (time != null && time != "") {
                message.setSendDate(DateUtils.stringToLong(time, "yyyy-MM-dd hh:mm:ss"));
            }

            messageService.save(message);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(HttpServletRequest request, Integer id) {
        if (id != null) {
            messageService.delete(messageService.queryByPK(id));
            return 1;
        }
        return 0;
    }

    @RequestMapping("/deleteBatch")
    @ResponseBody
    public Integer deleteBatch(HttpServletRequest request, String ids) {
        try {
            // 将界面上的id数组格式的字符串解析成int类型的数组
            Long[] arrayId = JsonUtil.json2Obj(ids, Long[].class);
            for (Long id : arrayId) {

                Message message = messageService.queryByPK(id);
                messageService.delete(message);
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
