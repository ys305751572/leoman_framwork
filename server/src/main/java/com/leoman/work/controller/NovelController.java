package com.leoman.work.controller;

import com.leoman.barrage.service.BarrageService;
import com.leoman.category.service.CategoryService;
import com.leoman.category.entity.Category;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.entity.FileBo;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.service.UserLoginService;
import com.leoman.user.service.UserService;
import com.leoman.utils.FileUtil;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.WebUtil;
import com.leoman.work.entity.*;
import com.leoman.work.service.*;
import com.leoman.work.service.impl.WorkServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
@Controller
@RequestMapping(value = "admin/novel")
public class NovelController extends GenericEntityController<Work, Work, WorkServiceImpl> {

    @Autowired
    private WorkService workService;

    @Autowired
    private WorkNovelService workNovelService;

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
        return "novel/list";
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     String name,
                     Long typeList,
                     Integer status,
                     Integer draw,
                     Integer start,
                     Integer length) {
        try {
            List<WorkNovel> workNovelList = new ArrayList<WorkNovel>();
            int pageNum = getPageNum(start, length);

            Page<Work> page = workService.page(name, typeList, status, 5, pageNum, length);
            for (Work work : page.getContent()) {
                workNovelList = workNovelService.findByWorkId(work.getId());
                if (workNovelList.size() != 0) {
                    work.setWorkNovel(workNovelList.get(workNovelList.size() - 1));
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
     * 章节列表页面
     *
     * @return
     */
    @RequestMapping(value = "/indexNovel")
    public String indexNovel(Long workId, Model model) {

        List<WorkNovel> workNovelList = new ArrayList<WorkNovel>();
        Work work = workService.queryByPK(workId);

        workNovelList = workNovelService.findByWorkId(work.getId());
        if (workNovelList.size() != 0) {
            work.setWorkNovel(workNovelList.get(workNovelList.size() - 1));
        }
        model.addAttribute("work", work);

        return "novel/novelList";
    }

    /**
     * 查询章节列表
     */
    @RequestMapping(value = "/novelList")
    public void novelList(HttpServletRequest request,
                          HttpServletResponse response,
                          Long workId,
                          Integer draw,
                          Integer start,
                          Integer length) {
        try {

            int pageNum = getPageNum(start, length);

            Page<WorkNovel> page = workNovelService.page(workId, pageNum, length);

            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
            WebUtil.print(response, emptyData);
        }
    }

    /**
     * 更新下一章
     *
     * @return
     */
    @RequestMapping(value = "/update")
    public String update(Model model, Long workId, Integer series) {

        Work work = workService.queryByPK(workId);

        model.addAttribute("work",work);
        model.addAttribute("series",series + 1);

        return "novel/update";
    }

    /**
     * 编辑下一章
     *
     * @return
     */
    @RequestMapping(value = "/updateEdit")
    public String updateEdit(Model model, Long novelId) {

        WorkNovel workNovel = workNovelService.queryByPK(novelId);
        Work work = workNovel.getWork();
        Integer series = workNovel.getSeries();

        workNovel.setDetail(workNovel.getDetail().replaceAll("&lt", "<").replaceAll("&gt", ">"));

        model.addAttribute("workNovel",workNovel);
        model.addAttribute("work",work);
        model.addAttribute("series",series);

        return "novel/update";
    }

    /**
     * 保存更新下一章
     *
     * @return
     */
    @RequestMapping(value = "/updateSave")
    @ResponseBody
    public Integer updateSave(HttpServletRequest request, Long workId, Long workNovelId, String novel,String content, Integer series, Integer status) throws UnsupportedEncodingException {

        /*String msg = request.getParameter("content");

        String str = new String(msg.getBytes("ISO-8859-1"), "UTF-8");*/

        WorkNovel workNovel = null;
        if (workNovelId == null) {
            workNovel = new WorkNovel();
            workNovel.setCreateDate(System.currentTimeMillis());

        }else {
            workNovel = workNovelService.queryByPK(workNovelId);
            workNovel.setUpdateDate(System.currentTimeMillis());
        }

        Work work = workService.queryByPK(workId);
        if (status == null) {
            work.setIsEnd(0);
        } else {
            work.setIsEnd(status);
        }
        workService.update(work);

        workNovel.setSeries(series);
        workNovel.setWork(work);
        workNovel.setName(novel);
        workNovel.setDetail(content);
        workNovel.setUpdateDate(System.currentTimeMillis());
        workNovelService.save(workNovel);

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
     * 查询类型列表
     */
    @RequestMapping(value = "/typeList")
    @ResponseBody
    public List<Category> typeList() {

        List<Category> typeList = categoryService.findByType(5);

        return typeList;
    }


    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        List<UserInfo> creatorList = userService.findByStatus(1);
        model.addAttribute("creatorList", creatorList);

        return "novel/add";
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

            //WorkCreator workCreator = workCreatorService.findByWorkId(workId);

            List<WorkCreator> workCreatorList =  workCreatorService.findWorkId(workId);

            List<WorkSurround> workSurroundList = workSurroundService.findByWorkId(workId);

            List<UserInfo> creatorList = userService.findByStatus(1);
            model.addAttribute("creatorList", creatorList);

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
        return "novel/add";
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
                        String[] name2,
                        String[] description2,
                        String[] linkUrl,
                        String[] names,
                        String[] descriptions,
                        String[] linkUrls,
                        MultipartRequest multipartRequest) throws IOException {

        /*Work work = null;

        if (null == id) {
            work = new Work();
            work.setCreateDate(System.currentTimeMillis());
        } else {
            work = workService.queryByPK(id);

            // 先删除原有的分类数量
            Category category1 = work.getCategory();
            category1.setCount(category1.getCount() - 1);
            categoryService.update(category1);
        }

        work.setName(name);
        work.setSeriesCount(seriesCount);

        // 后新增分类数量
        Category category1 = categoryService.queryByPK(category);
        category1.setCount(category1.getCount() + 1);
        categoryService.update(category1);
        work.setCategory(category1);

        work.setDescription(description);
        workService.save(work);
        MultipartFile multipartFile = multipartRequest.getFile("imageFile");
        if (null != multipartFile) {
            FileBo fileBo = FileUtil.save(multipartFile);
            work.setCover(fileBo.getPath());
        }
        workService.update(work);

        *//*WorkCreator workCreator = null;
        WorkCreator workCreators = workCreatorService.findByWorkId(id);
        if (workCreators == null) {
            workCreator = new WorkCreator();
        } else {
            workCreator = workCreators;
        }
        workCreator.setWork(work);
        //workCreator.setUserLogin(userService.queryByPK(creatorId).getUserLogin());
        workCreator.setUserInfo(userService.queryByPK(creatorId));*//*
        for (int i=0; i<creator.length - 1; i++) {
            WorkCreator workCreator = new WorkCreator();
            workCreator.setWork(work);
            workCreator.setPraise(0);
            workCreator.setUserInfo(userService.findOneByNickname(creator[i]));
            workCreatorService.update(workCreator);
        }*/

        /*//修改周边
        List<WorkSurround> workSurroundList = workSurroundService.findByWorkId(work.getId());
        if (workSurroundList.size() > 0) {
            for (int i = 0; i < names.length; i++) {
                if (StringUtils.isNotEmpty(names[i])) {
                    workSurroundList.get(i).setName(names[i]);
                    workSurroundList.get(i).setWork(work);
                    if (StringUtils.isNotEmpty(descriptions[i])) {
                        workSurroundList.get(i).setDescription(descriptions[i]);
                    }
                    if (StringUtils.isNotBlank(linkUrls[i])) {
                        workSurroundList.get(i).setLinkUrl(linkUrls[i]);
                    }
                    MultipartFile file = multipartRequest.getFile("covers" + workSurroundList.get(i).getId());
                    if (null != file) {
                        FileBo fileBo = FileUtil.save(file);
                        workSurroundList.get(i).setCover(fileBo.getPath());
                    }
                    workSurroundService.save(workSurroundList.get(i));
                }
            }
        }

        //新增周边
        for (int i = 0; i < name2.length; i++) {
            if (StringUtils.isNotEmpty(name2[i])) {
                WorkSurround workSurround = new WorkSurround();
                workSurround.setName(name2[i]);
                workSurround.setWork(work);
                if (StringUtils.isNotEmpty(description2[i])) {
                    workSurround.setDescription(description2[i]);
                }
                if (StringUtils.isNotBlank(linkUrl[i])) {
                    workSurround.setLinkUrl(linkUrl[i]);
                }
                List<MultipartFile> multipartFiles = multipartRequest.getFiles("cover");
                if (multipartFiles.size() > 0) {
                    FileBo fileBo = FileUtil.save(multipartFiles.get(i));
                    workSurround.setCover(fileBo.getPath());
                }
                workSurroundService.save(workSurround);
            }
        }*/

        workSurroundService.saveAll(id, name, seriesCount, category, description, creator, name2, description2,
                linkUrl, names, descriptions, linkUrls, multipartRequest);

        return 1;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    private Integer delete(Long novelId) {

        WorkNovel workNovel = workNovelService.queryByPK(novelId);
        workNovelService.delete(workNovel);

        return 1;
    }

    /**
     * 预览
     *
     * @param novelId
     * @param model
     */
    @RequestMapping(value = "/detail")
    private String detail(Model model, Long novelId) {

        WorkNovel workNovel = workNovelService.queryByPK(novelId);
        workNovel.setDetail(workNovel.getDetail().replaceAll("&lt", "<").replaceAll("&gt", ">"));

        model.addAttribute("workNovel",workNovel);

        return "novel/detail";
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

}
