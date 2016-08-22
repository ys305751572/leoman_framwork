package com.leoman.barrage.controller;

import com.leoman.barrage.entity.Barrage;
import com.leoman.barrage.service.BarrageService;
import com.leoman.barrage.service.impl.BarrageServiceImpl;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.service.UserService;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.WebUtil;
import com.leoman.work.entity.Work;
import com.leoman.work.entity.WorkComic;
import com.leoman.work.entity.WorkVideo;
import com.leoman.work.service.WorkComicService;
import com.leoman.work.service.WorkVideoService;
import com.leoman.work.service.impl.WorkServiceImpl;
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
@RequestMapping(value = "admin/barrage")
public class BarrageController extends GenericEntityController<Barrage, Barrage, BarrageServiceImpl> {

    @Autowired
    private BarrageService barrageService;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkVideoService workVideoService;

    @Autowired
    private WorkComicService workComicService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "barrage/list";
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

            Page<Barrage> page = barrageService.pageList(name, mobile, 0, pageNum, length);
            for (Barrage barrage : page.getContent()) {
                WorkVideo workVideo = workVideoService.queryByPK(barrage.getWorkId());
                barrage.setWorkVideo(workVideo);
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
    private Integer delete(Long barrageId) {

        Barrage barrage = barrageService.queryByPK(barrageId);
        barrageService.delete(barrage);

        return 1;
    }

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/indexComic")
    public String indexComic() {
        return "barrage/listComic";
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

            Page<Barrage> page = barrageService.pageList(name, mobile, 1, pageNum, length);
            for (Barrage barrage : page.getContent()) {
                WorkComic workComic = workComicService.queryByPK(barrage.getWorkId());
                barrage.setWorkComic(workComic);
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
            Barrage barrage = barrageService.queryByPK(id);
            barrageService.delete(barrage);
        }
        return 1;
    }
}
