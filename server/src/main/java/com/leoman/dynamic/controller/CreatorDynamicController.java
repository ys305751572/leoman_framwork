package com.leoman.dynamic.controller;

import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.creatordynamic.entity.CreatorDynamic;
import com.leoman.dynamic.service.CreatorDynamicService;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.service.UserService;
import com.leoman.utils.DateUtils;
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
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/16 0016.
 */
@Controller
@RequestMapping(value = "admin/creatorDynamic")
public class CreatorDynamicController extends GenericEntityController<CreatorDynamic, CreatorDynamic, CreatorDynamicService> {

    @Autowired
    private CreatorDynamicService creatorDynamicService;

    @Autowired
    private UserService userService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "dynamic/list";
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

            Page<CreatorDynamic> page = creatorDynamicService.page(pageNum, length);

            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
            WebUtil.print(response, emptyData);
        }
    }

    /**
     * 查询主创列表
     */
    @RequestMapping(value = "/creatorList")
    @ResponseBody
    public List<UserInfo> creatorList() {

        List<UserInfo> creatorList = userService.findByStatus(1);

        return creatorList;
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Long dynamicId) {

        CreatorDynamic creatorDynamic = creatorDynamicService.queryByPK(dynamicId);
        creatorDynamicService.delete(creatorDynamic);

        return 1;
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
            CreatorDynamic creatorDynamic = creatorDynamicService.queryByPK(id);
            creatorDynamicService.delete(creatorDynamic);
        }
        return 1;
    }

    /**
     * 保存
     *
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Integer save(Long creatorId, Integer status) {
        try {
            CreatorDynamic creatorDynamic = new CreatorDynamic();
            creatorDynamic.setUserInfo(userService.queryByPK(creatorId));
            creatorDynamic.setStatus(status);
            creatorDynamic.setSource(1);
            creatorDynamic.setDate(DateUtils.longToString(System.currentTimeMillis(), "yyyy-MM-dd"));
            creatorDynamicService.save(creatorDynamic);

            return 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
