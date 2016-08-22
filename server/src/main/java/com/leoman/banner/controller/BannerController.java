package com.leoman.banner.controller;

import com.leoman.banner.entity.Banner;
import com.leoman.banner.service.BannerService;
import com.leoman.banner.service.impl.BannerServiceImpl;
import com.leoman.category.entity.Category;
import com.leoman.category.service.CategoryService;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.entity.FileBo;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.post.entity.PostBase;
import com.leoman.post.service.PostBaseService;
import com.leoman.utils.FileUtil;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.WebUtil;
import com.leoman.work.entity.Work;
import com.leoman.work.entity.WorkComic;
import com.leoman.work.entity.WorkNovel;
import com.leoman.work.entity.WorkVideo;
import com.leoman.work.service.WorkComicService;
import com.leoman.work.service.WorkNovelService;
import com.leoman.work.service.WorkService;
import com.leoman.work.service.WorkVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2016/6/29 0029.
 */
@Controller
@RequestMapping(value = "admin/banner")
public class BannerController extends GenericEntityController<Banner, Banner, BannerServiceImpl> {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private WorkService workService;

    @Autowired
    private WorkVideoService workVideoService;

    @Autowired
    private WorkNovelService workNovelService;

    @Autowired
    private WorkComicService workComicService;

    @Autowired
    private PostBaseService postBaseService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "banner/list";
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

            Page<Banner> page = bannerService.page(pageNum, length);

            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
            WebUtil.print(response, emptyData);
        }
    }

    /**
     * 新增
     *
     * @return
     */
    @RequestMapping("/add")
    public String add() {

        return "banner/add";
    }

    /**
     * 编辑
     *
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Long bannerId, Model model) {

        Banner banner = bannerService.queryByPK(bannerId);
        model.addAttribute("banner", banner);

        return "banner/add";
    }

    /**
     * 根据类型查找对应分类列表(视频、小说。漫画)
     *
     * @return
     */
    @RequestMapping(value = "/categoryList")
    @ResponseBody
    public List<Work> categoryList(Integer typeId) {

        List<Work> workList = new ArrayList<Work>();
        List<Work> works = null;

        if (typeId == 1) {
            List<Category> categoryList = categoryService.findByType(4);
            for (Category category : categoryList) {

                works = workService.iFindList(category.getId());
                workList.addAll(works);
            }
        } else if (typeId == 2) {
            List<Category> categoryList = categoryService.findByType(5);
            for (Category category : categoryList) {

                works = workService.iFindList(category.getId());
                workList.addAll(works);
            }
        } else if (typeId == 3) {
            List<Category> categoryList = categoryService.findByType(6);
            for (Category category : categoryList) {

                works = workService.iFindList(category.getId());
                workList.addAll(works);
            }
        }

        /*for (int i = 0; i < workList.size(); i++) {
            // 从大到小
            for (int j = i + 1; j < workList.size(); j++) {
                if (workList.get(i).getUpdateDate() - workList.get(j).getUpdateDate() > 0) {
                    Work tmp = workList.get(i);
                    workList.get(i).setName(workList.get(j).getName());
                    workList.get(i).setId(workList.get(j).getId());
                    workList.get(j).setName(tmp.getName());
                    workList.get(j).setId(tmp.getId());
                }
            }
        }*/

        return workList;
    }

    /**
     * 根据类型、分类查找对应作品列表
     *
     * @return
     */
    @RequestMapping(value = "/seriesList")
    @ResponseBody
    public List<Map<String, Object>> seriesList(Integer typeId, Long categoryId, Model model) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;

        List<WorkVideo> workVideos = null;
        List<WorkComic> workComics = null;
        List<WorkNovel> workNovels = null;
        if (typeId == 1) {
            workVideos = workVideoService.findByWorkId(categoryId);

            for (WorkVideo workVideo : workVideos) {
                map = new HashMap<String, Object>();
                map.put("id", workVideo.getId());
                map.put("name", workVideo.getName());

                list.add(map);
            }
        } else if (typeId == 3) {
            workComics = workComicService.findByWorkId(categoryId);

            for (WorkComic workComic : workComics) {
                map = new HashMap<String, Object>();
                map.put("id", workComic.getId());
                map.put("name", workComic.getName());

                list.add(map);
            }
        } else if (typeId == 2) {
            workNovels = workNovelService.findByWorkId(categoryId);

            for (WorkNovel workNovel : workNovels) {
                map = new HashMap<String, Object>();
                map.put("id", workNovel.getId());
                map.put("name", workNovel.getName());

                list.add(map);
            }
        }

        return list;
    }

    /**
     * 根据类型查找对应分类列表(帖子)
     *
     * @return
     */
    @RequestMapping(value = "/categorys")
    @ResponseBody
    public List<Category> categorys() {

        List<Category> categoryList = categoryService.findByType(1);

        return categoryList;
    }

    /**
     * 根据类型查找帖子列表
     *
     * @return
     */
    @RequestMapping(value = "/loadContent")
    @ResponseBody
    public List<PostBase> loadContent(Long categoryId) {

        List<PostBase> postBaseList = postBaseService.findByCategoryId(categoryId);

        return postBaseList;
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Long bannerId) {

        Banner banner = bannerService.queryByPK(bannerId);
        bannerService.delete(banner);

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
            Banner banner = bannerService.queryByPK(id);
            bannerService.delete(banner);
        }
        return 1;
    }

    /**
     * 保存
     *
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Integer save(Long id,
                        String linkUrl,
                        Integer position,
                        Long categoryId,
                        Long seriesId,
                        @RequestParam(value = "imageFile", required = false) MultipartFile multipartFile) {

        Banner banner = null;
        if (id == null) {
            banner = new Banner();
            banner.setCreateDate(System.currentTimeMillis());
        } else {
            banner = bannerService.queryByPK(id);
        }

//        MultipartFile multipartFile1 = multipartRequest.getFile("cover");
        try {
            if (null != multipartFile) {
                FileBo fileBo = FileUtil.save(multipartFile);
                banner.setCover(fileBo.getPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (linkUrl == "") {
            banner.setLinkUrl(null);
        }else {
            banner.setLinkUrl(linkUrl);
        }
        banner.setPosition(position);
        banner.setCategoryId(categoryId);
        banner.setWorkId(seriesId);
        banner.setUpdateDate(System.currentTimeMillis());

        bannerService.save(banner);
        bannerService.update(banner);

        return 1;
    }
}
