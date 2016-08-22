package com.leoman.comment.controller;

import com.leoman.barrage.entity.Barrage;
import com.leoman.comment.service.CommentService;
import com.leoman.comment.service.impl.CommentServiceImpl;
import com.leoman.comment.entity.Comment;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.service.UserService;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.WebUtil;
import com.leoman.work.entity.WorkComic;
import com.leoman.work.entity.WorkNovel;
import com.leoman.work.entity.WorkVideo;
import com.leoman.work.service.WorkComicService;
import com.leoman.work.service.WorkNovelService;
import com.leoman.work.service.WorkVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
@Controller
@RequestMapping(value = "admin/comment")
public class CommentController extends GenericEntityController<Comment, Comment, CommentServiceImpl> {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkVideoService workVideoService;

    @Autowired
    private WorkComicService workComicService;

    @Autowired
    private WorkNovelService workNovelService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "comment/list";
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     String name,
                     String mobile,
                     Integer draw,
                     Integer start,
                     Integer length) {
        try {

            int pageNum = getPageNum(start, length);

            Page<Comment> page = commentService.pageList(name, mobile, 0, pageNum, length);
            for (Comment comment : page.getContent()) {
                WorkVideo workVideo = workVideoService.queryByPK(comment.getWorksId());
                comment.setWorkVideo(workVideo);

                /*UserInfo userInfo = userService.findOneByUserId(comment.getFromUser().getId());
                comment.setUserInfo(userInfo);*/
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
    private Integer delete(Long commentId) {

        Comment comment = commentService.queryByPK(commentId);
        commentService.delete(comment);

        return 1;
    }

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/indexNovel")
    public String indexNovel() {
        return "comment/listNovel";
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/listNovel")
    public void listNovel(HttpServletRequest request,
                     HttpServletResponse response,
                     String name,
                     String mobile,
                     Integer draw,
                     Integer start,
                     Integer length) {
        try {

            int pageNum = getPageNum(start, length);

            Page<Comment> page = commentService.pageList(name, mobile, 1, pageNum, length);
            for (Comment comment : page.getContent()) {
                WorkNovel workNovel = workNovelService.queryByPK(comment.getWorksId());
                comment.setWorkNovel(workNovel);

                /*UserInfo userInfo = userService.findOneByUserId(comment.getFromUser().getId());
                comment.setUserInfo(userInfo);*/
            }
            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
            WebUtil.print(response, emptyData);
        }
    }

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/indexComic")
    public String indexComic() {
        return "comment/listComic";
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/listComic")
    public void listComic(HttpServletRequest request,
                     HttpServletResponse response,
                     String name,
                     String mobile,
                     Integer draw,
                     Integer start,
                     Integer length) {
        try {

            int pageNum = getPageNum(start, length);

            Page<Comment> page = commentService.pageList(name, mobile, 2, pageNum, length);
            for (Comment comment : page.getContent()) {
                WorkComic workComic = workComicService.queryByPK(comment.getWorksId());
                comment.setWorkComic(workComic);

                /*UserInfo userInfo = userService.findOneByUserId(comment.getFromUser().getId());
                comment.setUserInfo(userInfo);*/
            }
            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
            WebUtil.print(response, emptyData);
        }
    }

    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ResponseBody
    public Integer deleteBatch(String ids) {
        Long[] arrayId = JsonUtil.json2Obj(ids, Long[].class);
        for (Long id : arrayId) {
            Comment comment = commentService.queryByPK(id);
            commentService.delete(comment);
        }
        return 1;
    }
}
