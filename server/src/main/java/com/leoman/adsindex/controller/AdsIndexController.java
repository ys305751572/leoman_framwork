package com.leoman.adsindex.controller;

import com.leoman.adsindex.entity.AdsIndex;
import com.leoman.adsindex.service.AdsIndexService;
import com.leoman.adsindex.service.impl.AdsIndexServiceImpl;
import com.leoman.banner.entity.Banner;
import com.leoman.category.entity.Category;
import com.leoman.category.service.CategoryService;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.entity.FileBo;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.post.entity.PostBase;
import com.leoman.post.service.PostBaseService;
import com.leoman.resource.service.GameService;
import com.leoman.resource.service.MusicService;
import com.leoman.resource.service.StillService;
import com.leoman.resources.entity.GameResource;
import com.leoman.resources.entity.MusicResource;
import com.leoman.resources.entity.StillResource;
import com.leoman.utils.FileUtil;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.WebUtil;
import com.leoman.welfare.entity.Welfare;
import com.leoman.welfare.service.WelfareService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
@Controller
@RequestMapping(value = "admin/adsIndex")
public class AdsIndexController extends GenericEntityController<AdsIndex, AdsIndex, AdsIndexServiceImpl>{

    @Autowired
    private AdsIndexService adsIndexService;

    @Autowired
    private WorkService workService;

    @Autowired
    private WorkVideoService workVideoService;

    @Autowired
    private WorkNovelService workNovelService;

    @Autowired
    private WorkComicService workComicService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostBaseService postBaseService;

    @Autowired
    private StillService stillService;

    @Autowired
    private MusicService musicService;

    @Autowired
    private GameService gameService;

    @Autowired
    private WelfareService welfareService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "adsIndex/list";
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

            Page<AdsIndex> page = adsIndexService.page(pageNum, length);

            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
            WebUtil.print(response, emptyData);
        }
    }

    /**
     * 新增
     *//*
    @RequestMapping(value = "/add")
    public String add() {
        return "adsIndex/add";
    }*/

    /**
     * 编辑
     */
    @RequestMapping(value = "/edit")
    public String edit(Long adsIndexId, Model model) {

        AdsIndex adsIndex = adsIndexService.queryByPK(adsIndexId);
        model.addAttribute("adsIndex", adsIndex);

        return "adsIndex/add";
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail")
    public String detail(Long adsIndexId, Model model) {

        /*List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;*/

        AdsIndex adsIndex = adsIndexService.queryByPK(adsIndexId);
        model.addAttribute("adsIndex", adsIndex);

        if (adsIndex.getPosition() == 0) {

            Category category = categoryService.queryByPK(adsIndex.getCategoryId());
            PostBase postBase = postBaseService.queryByPK(adsIndex.getWorkId());
            model.addAttribute("category", category);
            model.addAttribute("postBase", postBase);

        } else if (adsIndex.getPosition() == 1) {

            Work work = workService.queryByPK(adsIndex.getCategoryId());
            WorkVideo workVideo = workVideoService.queryByPK(adsIndex.getWorkId());
            model.addAttribute("work", work);
            model.addAttribute("workVideo", workVideo);

        }else if (adsIndex.getPosition() == 2) {

            Work work = workService.queryByPK(adsIndex.getCategoryId());
            WorkNovel workNovel = workNovelService.queryByPK(adsIndex.getWorkId());
            model.addAttribute("work", work);
            model.addAttribute("workNovel", workNovel);

        }else if (adsIndex.getPosition() == 3) {

            Work work = workService.queryByPK(adsIndex.getCategoryId());
            WorkComic workComic = workComicService.queryByPK(adsIndex.getWorkId());
            model.addAttribute("work", work);
            model.addAttribute("workComic", workComic);

        }else if (adsIndex.getPosition() == 4) {

            String type = "";
            if (adsIndex.getCategoryId() == 1) {
                type = "剧照";
            }else if (adsIndex.getCategoryId() == 2) {
                type = "音乐";
            }else if (adsIndex.getCategoryId() == 3) {
                type = "游戏";
            }
            model.addAttribute("type", type);

        }else if (adsIndex.getPosition() == 5) {

            String type = "";
            if (adsIndex.getCategoryId() == 1) {
                type = "铃声";
            }else if (adsIndex.getCategoryId() == 2) {
                type = "经验值";
            }else if (adsIndex.getCategoryId() == 3) {
                type = "实物";
            }else if (adsIndex.getCategoryId() == 4) {
                type = "表情包";
            }else if (adsIndex.getCategoryId() == 5) {
                type = "商城购买";
            }else if (adsIndex.getCategoryId() == 6) {
                type = "游戏兑换码";
            }

            model.addAttribute("type", type);
            Welfare welfare = welfareService.queryByPK(adsIndex.getWorkId());
            model.addAttribute("welfare", welfare);

        }

        return "adsIndex/detail";
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Integer save(Long id,
                        String title,
                        String subTitle,
                        Integer position,
                        Long categoryId,
                        Long seriesId,
                        @RequestParam(value = "imageFile", required = false) MultipartFile multipartFile) {

        AdsIndex adsIndex = null;
        if (id == null) {
            adsIndex = new AdsIndex();
            adsIndex.setCreateDate(System.currentTimeMillis());
        }else {
            adsIndex = adsIndexService.queryByPK(id);
            adsIndex.setUpdateDate(System.currentTimeMillis());
        }

        try {
            if (null != multipartFile) {
                FileBo fileBo = FileUtil.save(multipartFile);
                adsIndex.setCover(fileBo.getPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        adsIndex.setTitle(title);
        adsIndex.setSubTitle(subTitle);
        adsIndex.setPosition(position);
        adsIndex.setCategoryId(categoryId);
        adsIndex.setWorkId(seriesId);

        adsIndexService.save(adsIndex);
        return 1;
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Long adsIndexId) {

        AdsIndex adsIndex = adsIndexService.queryByPK(adsIndexId);
        adsIndexService.delete(adsIndex);

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
            AdsIndex adsIndex = adsIndexService.queryByPK(id);
            adsIndexService.delete(adsIndex);
        }
        return 1;
    }

    /**
     * 根据类型查找资源列表
     *
     * @return
     */
    @RequestMapping(value = "/loadResource")
    @ResponseBody
    public List<Map<String, Object>> loadResource(Long categoryId) {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;

        if (categoryId == 1) {
            List<StillResource> stillResourceList = stillService.queryAll();
            for (StillResource stillResource : stillResourceList) {
                map = new HashMap<String, Object>();
                map.put("id", stillResource.getCategory().getId());
                map.put("name", stillResource.getCategory().getName());
                list.add(map);
            }

        }

        if (categoryId == 2) {
            List<MusicResource> musicResourceList = musicService.queryAll();
            for (MusicResource musicResource : musicResourceList) {
                map = new HashMap<String, Object>();
                map.put("id", musicResource.getCategory().getId());
                map.put("name", musicResource.getCategory().getName());
                list.add(map);
            }
        }

        if (categoryId == 3) {
            List<GameResource> gameResourceList = gameService.queryAll();
            for (GameResource gameResource : gameResourceList) {
                map = new HashMap<String, Object>();
                map.put("id", gameResource.getId());
                map.put("name", gameResource.getName());
                list.add(map);
            }
        }

        return list;
    }

    /**
     * 根据类型查找福利社列表
     *
     * @return
     */
    @RequestMapping(value = "/loadWelfare")
    @ResponseBody
    public List<Welfare> loadWelfare(Integer categoryId) {

        List<Welfare> list = welfareService.findByType(categoryId - 1);

        return list;
    }
}
