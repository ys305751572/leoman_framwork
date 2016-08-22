package com.leoman.resource.controller;

import com.leoman.category.entity.Category;
import com.leoman.category.service.CategoryService;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.entity.FileBo;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.resource.PlayerMusic;
import com.leoman.resource.service.MusicService;
import com.leoman.resource.service.StillService;
import com.leoman.resource.service.impl.StillServiceImpl;
import com.leoman.resources.entity.MusicResource;
import com.leoman.resources.entity.StillResource;
import com.leoman.utils.FileUtil;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
@Controller
@RequestMapping(value = "admin/music")
public class MusicController extends GenericEntityController<StillResource, StillResource, StillServiceImpl> {

    @Autowired
    private MusicService musicService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "resource/listMusic";
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

            Page<MusicResource> page = musicService.page(pageNum, length);

            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
            WebUtil.print(response, emptyData);
        }
    }

    /**
     * 音乐类别
     *
     * @return
     */
    @RequestMapping(value = "/categoryList")
    @ResponseBody
    public List<Category> categoryList() {

        List<Category> categoryList = categoryService.findType(3);
        return categoryList;
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Long musicId) {

        MusicResource musicResource = musicService.queryByPK(musicId);
        musicService.delete(musicResource);

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
            MusicResource musicResource = musicService.queryByPK(id);
            musicService.delete(musicResource);
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
    public Integer save(Long categoryId,
                        String name,
                        String singer,
                        @RequestParam(value = "imageFile",required = false) MultipartFile file) throws IOException {

        MusicResource musicResource = new MusicResource();
        Category category = categoryService.queryByPK(categoryId);
        category.setCount(category.getCount() + 1);
        categoryService.update(category);
        musicResource.setCategory(category);
        musicResource.setName(name);
        musicResource.setSinger(singer);

        MultipartFile multipartFile = file;
        if (null != multipartFile) {
            FileBo fileBo = FileUtil.save(multipartFile);
            musicResource.setImageUrl(fileBo.getPath());
        }

        musicService.insert(musicResource);
        return 1;
    }

    /**
     * 试听
     *
     * @return
     */
    @RequestMapping(value = "/listen")
    @ResponseBody
    public Integer listen(Long musicId) {
        List mp3List = new ArrayList();
        //mp3List.add(musicService.queryByPK(musicId).getUrl());
        mp3List.add("F:\\music\\G.E.M. 邓紫棋 - 泡沫.mp3");

        PlayerMusic pm = new PlayerMusic(mp3List);
        pm.start();

        MusicResource musicResource = musicService.queryByPK(musicId);
        musicResource.setNum(musicResource.getNum() + 1);
        musicService.update(musicResource);

        return 1;
    }
}
