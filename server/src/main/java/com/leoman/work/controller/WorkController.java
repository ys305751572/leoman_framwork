package com.leoman.work.controller;

import com.leoman.barrage.service.BarrageService;
import com.leoman.barrage.entity.Barrage;
import com.leoman.category.service.CategoryService;
import com.leoman.category.entity.Category;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.entity.FileBo;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.sohuapi.api.SohuApi;
import com.leoman.sohuapi.entity.AlbumFromSohu;
import com.leoman.sohuapi.entity.VideoFromSohu;
import com.leoman.sohuapi.entity.VideoSubFromSohu;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.service.UserLoginService;
import com.leoman.user.service.UserService;
import com.leoman.utils.FileUtil;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.WebUtil;
import com.leoman.work.entity.Work;
import com.leoman.work.entity.WorkCreator;
import com.leoman.work.entity.WorkSurround;
import com.leoman.work.entity.WorkVideo;
import com.leoman.work.service.WorkCreatorService;
import com.leoman.work.service.WorkService;
import com.leoman.work.service.WorkSurroundService;
import com.leoman.work.service.WorkVideoService;
import com.leoman.work.service.impl.WorkServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
@Controller
@RequestMapping(value = "admin/work")
public class WorkController extends GenericEntityController<Work, Work, WorkServiceImpl> {

    @Autowired
    private WorkService workService;

    @Autowired
    private WorkVideoService workVideoService;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkSurroundService workSurroundService;

    @Autowired
    private WorkCreatorService workCreatorService;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private BarrageService barrageService;

    @Autowired
    private CategoryService categoryService;


    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "work/list";
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     String name,
                     Long type,
                     Integer status,
                     Integer draw,
                     Integer start,
                     Integer length) {
        try {
            List<WorkVideo> workVideoList = new ArrayList<WorkVideo>();
            int pageNum = getPageNum(start, length);

            Page<Work> page = workService.page(name, type, status, 4, pageNum, length);
            for (Work work : page.getContent()) {
                workVideoList = workVideoService.findByWorkId(work.getId());
                if (workVideoList.size() != 0) {
                    work.setWorkVideo(workVideoList.get(workVideoList.size()-1));
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
     * 新增
     */
    /*@RequestMapping(value = "/add")
    public String add(Model model) {

        List<UserInfo> creatorList = userService.findByStatus(1);
        model.addAttribute("creatorList", creatorList);

        return "work/add";
    }*/
    @RequestMapping(value = "/add")
    @ResponseBody
    public Integer add(Model model, Long id, Long categoryId) {

        Work work = workService.findBySoHuId(id);

        AlbumFromSohu albumFromSohu = SohuApi.apiFromSohuAlibum(id);
        if (work == null) {
            work = new Work();
            work.setCreateDate(System.currentTimeMillis());
        }
        work.setName(albumFromSohu.getAlbum_name());
        work.setCover(albumFromSohu.getHor_high_pic());
        work.setSeriesCount(albumFromSohu.getTotal_video_count() != null ? albumFromSohu.getTotal_video_count():0);
        work.setDescription(albumFromSohu.getAlbum_desc());
        work.setPlayNum(albumFromSohu.getPlay_count()  != null ? albumFromSohu.getPlay_count():0);
        work.setAlbumId(id);
        work.setUpdateCount(albumFromSohu.getPlay_count() != null ? albumFromSohu.getPlay_count() : 0);
        work.setUpdateCount(albumFromSohu.getLatest_video_count());
        work.setCategory(categoryService.queryByPK(categoryId));
        if (albumFromSohu.getTotal_video_count() == albumFromSohu.getLatest_video_count()) {
            work.setIsEnd(1);
        }
        workService.update(work);

        return 1;
    }

    /**
     * 编辑
     *
     * @param workId
     * @param model
     */
    @RequestMapping(value = "/edit")
    public String edit(Long workId, Model model) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

            Work work = workService.queryByPK(workId);

            List<WorkCreator> workCreatorList = workCreatorService.findWorkId(workId);

            List<WorkSurround> workSurroundList = workSurroundService.findByWorkId(workId);

            /*List<UserInfo> creatorList = userService.findByStatus(1);
            model.addAttribute("creatorList", creatorList);*/

            model.addAttribute("work", work);
            model.addAttribute("workCreatorList", workCreatorList);
            model.addAttribute("workSurroundList", workSurroundList);

            StringBuilder sb = new StringBuilder("");
            for (WorkCreator workCreator : workCreatorList) {
                sb.append(workCreator.getUserInfo().getId() + ",");
            }

            model.addAttribute("tempCreatorIds", sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "work/add";
    }

    /**
     * 剧集列表页面
     *
     * @return
     */
    @RequestMapping(value = "/indexVideo")
    public String indexVideo(Long workId, Model model) {

        Work work = workService.queryByPK(workId);

        List<WorkVideo> workVideoList = workVideoService.findByWorkId(work.getId());
        int page = workVideoList.size()/10;

        AlbumFromSohu albumFromSohu = SohuApi.apiFromSohuAlibum(work.getAlbumId());
        WorkVideo workVideo = null;
        VideoSubFromSohu videoSubFromSohu = SohuApi.apiFromSohuVideoList(work.getAlbumId(), page + 1, 10);
        for (VideoFromSohu videoFromSohu : videoSubFromSohu.getList()) {
            if (videoFromSohu.getVideo_order() > workVideoList.size()) {
                workVideo = new WorkVideo();
                workVideo.setName(videoFromSohu.getVideo_name());
                workVideo.setWork(work);
                workVideo.setLength(videoFromSohu.getTime_length());
                workVideo.setSeries(videoFromSohu.getVideo_order());
                workVideo.setLinkUrl(videoFromSohu.getUrl_html5());
                workVideoService.save(workVideo);
            }
        }

        model.addAttribute("work", work);

        return "work/videoList";
    }

    /**
     * 查询剧集列表
     */
    @RequestMapping(value = "/videoList")
    public void videoList(HttpServletRequest request,
                          HttpServletResponse response,
                          Long workId,
                          Integer draw,
                          Integer start,
                          Integer length) {
        try {

            int pageNum = getPageNum(start, length);

            Page<WorkVideo> page = workVideoService.page(workId, pageNum, length);

            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
            WebUtil.print(response, emptyData);
        }
    }

    /**
     * 更新下一集
     *
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Integer update(HttpServletRequest request, Long workId, Long workVideoId, Integer num, Integer status) throws UnsupportedEncodingException {

        String msg = request.getParameter("video");
        String msg2 = request.getParameter("videoName");

        String str = new String(msg.getBytes("ISO-8859-1"), "UTF-8");
        String str2 = new String(msg2.getBytes("ISO-8859-1"), "UTF-8");

        WorkVideo workVideo = null;
        if (workVideoId == null) {
            workVideo = new WorkVideo();
            workVideo.setCreateDate(System.currentTimeMillis());
        } else {
            workVideo = workVideoService.queryByPK(workVideoId);
        }

        Work work = workService.queryByPK(workId);
        if (status == null) {
            work.setIsEnd(0);
        } else {
            work.setIsEnd(status);
        }
        work.setUpdateDate(System.currentTimeMillis());
        workService.update(work);

        workVideo.setSeries(num);
        workVideo.setLinkUrl(str);
        workVideo.setName(str2);
        workVideo.setWork(work);
        workVideo.setUpdateDate(System.currentTimeMillis());
        workVideoService.save(workVideo);

        //workService.update(request, workId, workVideoId, num, status);
        return 1;
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
     * 查询视频主创列表
     */
    @RequestMapping(value = "/videoCreatorList")
    @ResponseBody
    public List<UserInfo> videoCreatorList(Long workId) {

        List<WorkCreator> videoCreatorList = workCreatorService.findWorkId(workId);
        List<UserInfo> creatorList = new ArrayList<UserInfo>();
        for (WorkCreator workCreator : videoCreatorList) {
            creatorList.add(workCreator.getUserInfo());
        }
        return creatorList;
    }

    /**
     * 查询类型列表
     */
    @RequestMapping(value = "/typeList")
    @ResponseBody
    public List<Category> typeList() {

        List<Category> typeList = categoryService.findByType(4);

        return typeList;
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Integer save(Long id,
                        String name,
                        Integer seriesCount,
                        Long category,
                        String description,
                        String[] creator,
                        String[] creators,
                        String[] name2,
                        String[] description2,
                        String[] linkUrl,
                        String[] names,
                        String[] descriptions,
                        String[] linkUrls,
                        MultipartRequest multipartRequest) throws IOException {

        workSurroundService.saveAll(id, name, seriesCount, category, description, creator, name2, description2,
                linkUrl, names, descriptions, linkUrls, multipartRequest);

        return 1;
    }

    /**
     * 新增主创弹幕
     */
    @RequestMapping(value = "/addBarrage")
    @ResponseBody
    public Integer addBarrage(HttpServletRequest request, Integer time, String detail, Long barrageId, Long workVideoId, Long creatorId) throws UnsupportedEncodingException {

        //String msg = request.getParameter("time");
        //String msg2 = request.getParameter("detail");

        //String str = new String(msg.getBytes("ISO-8859-1"), "UTF-8");
        //String str2 = new String(msg2.getBytes("ISO-8859-1"), "UTF-8");

        Barrage barrage = null;
        if (barrageId == null) {
            barrage = new Barrage();
        } else {
            barrage = barrageService.queryByPK(barrageId);
        }

        //barrage.setUserLogin(userService.queryByPK(creatorId).getUserLogin());
        barrage.setUserInfo(userService.queryByPK(creatorId));
        barrage.setContent(detail);
        barrage.setType(0);
        barrage.setTime(time);
        barrage.setWorkId(workVideoId);
        barrageService.save(barrage);

        Work work = workVideoService.queryByPK(workVideoId).getWork();
        work.setBarrageNum(work.getBarrageNum() + 1);
        workService.update(work);
        return 1;

    }

    /**
     * 主创弹幕列表页面
     *
     * @return
     */
    @RequestMapping(value = "/indexBarrage")
    public String indexBarrage(Long workId, Model model) {

        WorkVideo workVideo = workVideoService.queryByPK(workId);
        //WorkCreator workCreator = workCreatorService.findByWorkId(workVideo.getWork().getId());

        model.addAttribute("workVideo", workVideo);
        //model.addAttribute("workCreator", workCreator);

        return "work/barrageList";
    }

    /**
     * 查询弹幕列表
     */
    @RequestMapping(value = "/barrageList")
    public void barrageList(HttpServletRequest request,
                            HttpServletResponse response,
                            Long workId,
                            Integer draw,
                            Integer start,
                            Integer length) {
        try {

            int pageNum = getPageNum(start, length);

            Page<Barrage> page = barrageService.page(workId, pageNum, length);

            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
            WebUtil.print(response, emptyData);
        }
    }

    @RequestMapping(value = "/deleteBatch")
    @ResponseBody
    public Integer deleteBatch(String ids) {
        Long[] arrayId = JsonUtil.json2Obj(ids, Long[].class);
        for (Long id : arrayId) {
            Work work = workService.queryByPK(id);
            workService.delete(work);
        }
        return 1;
    }

    @RequestMapping(value = "/deleteCreator")
    @ResponseBody
    public Integer deleteCreator(Long workId) {

        WorkCreator workCreator = workCreatorService.queryByPK(workId);
        workCreatorService.delete(workCreator);

        return 1;
    }

    @RequestMapping(value = "/deleteSurround")
    @ResponseBody
    public Integer deleteSurround(Long id) {

        WorkSurround workSurround = workSurroundService.queryByPK(id);
        workSurroundService.delete(workSurround);

        return 1;
    }
}
