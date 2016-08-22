package com.leoman.post.controller;

import com.leoman.comment.entity.Comment;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.post.entity.PostComment;
import com.leoman.post.service.PostCommentService;
import com.leoman.post.service.impl.PostCommentServiceImpl;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.WebUtil;
import com.leoman.work.entity.WorkVideo;
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
@RequestMapping(value = "admin/postComment")
public class PostCommentController extends GenericEntityController<PostComment, PostComment, PostCommentServiceImpl> {

    @Autowired
    private PostCommentService postCommentService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "post/upCommentList";
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     String content,
                     String mobile,
                     Integer draw,
                     Integer start,
                     Integer length) {
        try {

            int pageNum = getPageNum(start, length);

            Page<PostComment> page = postCommentService.pageList(content, mobile, 3, 1, pageNum, length);
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
    private Integer delete(Long commentId) {

        PostComment comment = postCommentService.queryByPK(commentId);
        postCommentService.delete(comment);

        return 1;
    }

    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ResponseBody
    public Integer deleteBatch(String ids) {
        Long[] arrayId = JsonUtil.json2Obj(ids, Long[].class);
        for (Long id : arrayId) {
            PostComment comment = postCommentService.queryByPK(id);
            postCommentService.delete(comment);
        }
        return 1;
    }

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/indexPost")
    public String indexPost() {
        return "post/gfCommentList";
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/postList")
    public void postList(HttpServletRequest request,
                     HttpServletResponse response,
                     String name,
                     String mobile,
                     Integer draw,
                     Integer start,
                     Integer length) {
        try {

            int pageNum = getPageNum(start, length);

            Page<PostComment> page = postCommentService.page(name, mobile, null, 1, pageNum, length);
            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
            WebUtil.print(response, emptyData);
        }
    }

}
