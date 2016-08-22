package com.leoman.work.controller;

import com.leoman.category.service.CategoryService;
import com.leoman.category.entity.Category;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.entity.FileBo;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.service.UserService;
import com.leoman.utils.FileUtil;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.WebUtil;
import com.leoman.work.entity.*;
import com.leoman.work.service.*;
import com.leoman.work.service.impl.WorkServiceImpl;
import net.sf.json.JSONArray;
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
@RequestMapping(value = "admin/comic")
public class ComicController extends GenericEntityController<Work, Work, WorkServiceImpl> {

    @Autowired
    private WorkService workService;

    @Autowired
    private WorkComicService workComicService;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkSurroundService workSurroundService;

    @Autowired
    private WorkCreatorService workCreatorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private WorkComicImageService workComicImageService;


    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "comic/list";
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
            List<WorkComic> workComicList = new ArrayList<WorkComic>();
            int pageNum = getPageNum(start, length);

            Page<Work> page = workService.page(name, typeList, status, 6, pageNum, length);
            for (Work work : page.getContent()) {
                workComicList = workComicService.findByWorkId(work.getId());
                if (workComicList.size() != 0) {
                    work.setWorkComic(workComicList.get(workComicList.size() - 1));
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
     * 话集列表页面
     *
     * @return
     */
    @RequestMapping(value = "/indexComic")
    public String indexComic(Long workId, Model model) {

        List<WorkComic> workComicList = new ArrayList<WorkComic>();
        Work work = workService.queryByPK(workId);

        workComicList = workComicService.findByWorkId(work.getId());
        if (workComicList.size() != 0) {
            work.setWorkComic(workComicList.get(workComicList.size() - 1));
        }

        model.addAttribute("work", work);

        return "comic/comicList";
    }

    /**
     * 查询话集列表
     */
    @RequestMapping(value = "/comicList")
    public void comicList(HttpServletRequest request,
                          HttpServletResponse response,
                          Long workId,
                          Integer draw,
                          Integer start,
                          Integer length) {
        try {

            int pageNum = getPageNum(start, length);

            Page<WorkComic> page = workComicService.page(workId, pageNum, length);

            List<WorkComic> workComicList = page.getContent();
            List<WorkComicImage> workComicImageList = new ArrayList<WorkComicImage>();
            for (WorkComic workComic : workComicList) {

                workComicImageList =  workComicImageService.findByComicId(workComic.getId());
                workComic.setImageList(workComicImageList.size());
            }

            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
            WebUtil.print(response, emptyData);
        }
    }

    /**
     * 更新下一话
     *
     * @return
     */
    @RequestMapping(value = "/update")
    public String update(Model model, Long workId, Integer series) {

        Work work = workService.queryByPK(workId);

        model.addAttribute("work",work);
        model.addAttribute("series",series + 1);
        model.addAttribute("workComicImageList", JSONArray.fromObject(null));

        return "comic/update";
    }

    /**
     * 编辑下一话
     *
     * @return
     */
    @RequestMapping(value = "/updateEdit")
    public String updateEdit(Model model, Long comicId) {

        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        WorkComic workComic = workComicService.queryByPK(comicId);
        Work work = workComic.getWork();
        Integer series = workComic.getSeries();

        List<WorkComicImage> workComicImageList = workComicImageService.findByComicId(workComic.getId());
        for (WorkComicImage workComicImage : workComicImageList) {
            map = new HashMap<String, Object>();
            map.put("id", workComicImage.getId());
            map.put("path", workComicImage.getUrl());

            list.add(map);
        }

        model.addAttribute("workComic",workComic);
        model.addAttribute("work",work);
        model.addAttribute("series",series);
        model.addAttribute("workComicImageList", JSONArray.fromObject(list));

        return "comic/update";
    }

    /**
     * 上传缓存图片
     *
     * @return
     */
    @RequestMapping("/addTempImage")
    @ResponseBody
    public WorkComicImage addTempImage(MultipartRequest multipartRequest, Integer type) {
        WorkComicImage image = new WorkComicImage();

        try {
            MultipartFile multipartFile = multipartRequest.getFile("tempImage");

            // 验证图片格式
            String originalFileName = multipartFile.getOriginalFilename().toLowerCase();
            String fileType = originalFileName.substring(originalFileName.indexOf("."));

            List<String> list = new ArrayList<String>();
            list.add(".jpg");
            list.add(".gif");
            list.add(".jpeg");
            list.add(".png");
            list.add(".bmp");

            if (!list.contains(fileType)) {
                return image;
            }

            FileBo fileBo = FileUtil.save(multipartFile);
            image.setUrl(fileBo.getPath());


            workComicImageService.save(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;
    }

    /**
     * 保存更新下一话
     *
     * @return
     */
    @RequestMapping(value = "/updateSave")
    @ResponseBody
    public Integer updateSave(HttpServletRequest request,
                              Long workId,
                              Long workComicId,
                              String comic,
                              Integer series,
                              Integer status,
                              String tempAddImageIds1,
                              String tempDelImageIds) throws UnsupportedEncodingException {

        String[] addImageIds1 = tempAddImageIds1.split(",");
        String[] delImageIds = tempDelImageIds.split(",");

        WorkComic workComic = null;
        if (workComicId == null) {
            workComic = new WorkComic();
            workComic.setCreateDate(System.currentTimeMillis());

        }else {
            workComic = workComicService.queryByPK(workComicId);
            workComic.setUpdateDate(System.currentTimeMillis());
        }

        Work work = workService.queryByPK(workId);
        if (status == null) {
            work.setIsEnd(0);
        } else {
            work.setIsEnd(status);
        }
        workService.update(work);

        workComic.setSeries(series);
        workComic.setWork(work);
        workComic.setName(comic);
        workComic.setUpdateDate(System.currentTimeMillis());
        workComicService.update(workComic);

        // 保存漫画图片集合
        WorkComicImage image = null;
        for (String imageId : addImageIds1) {
            if (null != imageId && !imageId.equals("")) {
                image = workComicImageService.queryByPK(Long.parseLong(imageId));
                image.setWorkComic(workComic);

                workComicImageService.update(image);
            }
        }

        // 删除漫画图片集合
        WorkComicImage image2 = null;
        for (String imageId : delImageIds) {
            if (null != imageId && !imageId.equals("")) {
                image2 = workComicImageService.queryByPK(Long.parseLong(imageId));
                workComicImageService.delete(image2);
            }
        }

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

        List<Category> typeList = categoryService.findByType(6);

        return typeList;
    }


    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        List<UserInfo> creatorList = userService.findByStatus(1);
        model.addAttribute("creatorList", creatorList);

        return "comic/add";
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
        return "comic/add";
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

        for (int i=0; i<creator.length - 1; i++) {
            WorkCreator workCreator = new WorkCreator();
            workCreator.setPraise(0);
            workCreator.setWork(work);
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
    private Integer delete(Long comicId) {

        WorkComic workComic = workComicService.queryByPK(comicId);
        workComicService.delete(workComic);

        return 1;
    }

    /**
     * 预览
     *
     * @param comicId
     * @param model
     */
    @RequestMapping(value = "/detail")
    private String detail(Model model, Long comicId) {

        WorkComic workComic = workComicService.queryByPK(comicId);

        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        List<WorkComicImage> workComicImageList = workComicImageService.findByComicId(workComic.getId());
        workComic.setImageList(workComicImageList.size());
        /*for (WorkComicImage workComicImage : workComicImageList) {
            map = new HashMap<String, Object>();
            map.put("id", workComicImage.getId());
            map.put("path", workComicImage.getUrl());

            list.add(map);
        }*/

        model.addAttribute("workComic",workComic);
        model.addAttribute("workComicImageList",workComicImageList);
        //model.addAttribute("workComicImageList", JSONArray.fromObject(list));

        return "comic/detail";
    }

    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
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
