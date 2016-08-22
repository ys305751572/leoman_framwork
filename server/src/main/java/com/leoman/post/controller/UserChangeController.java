package com.leoman.post.controller;

import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.post.entity.PostBase;
import com.leoman.post.entity.PostComment;
import com.leoman.post.service.PostBaseService;
import com.leoman.post.service.PostCommentService;
import com.leoman.post.service.UserChangeService;
import com.leoman.post.service.impl.UserChangeServiceImpl;
import com.leoman.userchange.entity.UserChange;
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
 * Created by Administrator on 2016/7/20 0020.
 */
@Controller
@RequestMapping(value = "admin/userChange")
public class UserChangeController extends GenericEntityController<UserChange, UserChange, UserChangeServiceImpl> {

    @Autowired
    private UserChangeService userChangeService;

    @Autowired
    private PostBaseService postBaseService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "post/praizeList";
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     String mobile,
                     Integer type,
                     Integer draw,
                     Integer start,
                     Integer length) {
        try {

            int pageNum = getPageNum(start, length);

            Page<UserChange> page = userChangeService.page(mobile, type, 1, pageNum, length);
            for (UserChange userChange : page.getContent()) {
                PostBase postBase = postBaseService.queryByPK(userChange.getSourceId());
                if (postBase != null) {
                    if (postBase.getType() == 3) {
                        userChange.setContent(postBase.getContent());
                    }else {
                        userChange.setContent(postBase.getName());
                    }
                }
            }
            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
            WebUtil.print(response, emptyData);
        }
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    private Integer delete(Long praiseId) {

        UserChange userChange = userChangeService.queryByPK(praiseId);
        userChangeService.delete(userChange);

        return 1;
    }

    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ResponseBody
    public Integer deleteBatch(String ids) {
        Long[] arrayId = JsonUtil.json2Obj(ids, Long[].class);
        for (Long id : arrayId) {
            UserChange userChange = userChangeService.queryByPK(id);
            userChangeService.delete(userChange);
        }
        return 1;
    }

}
