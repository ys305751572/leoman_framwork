package com.leoman.resource.controller;

import com.leoman.category.entity.Category;
import com.leoman.category.service.CategoryService;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.bean.Result;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.entity.FileBo;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.resource.service.StillService;
import com.leoman.resource.service.impl.StillServiceImpl;
import com.leoman.resources.entity.StillResource;
import com.leoman.utils.FileUtil;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.WebUtil;
import com.leoman.work.entity.Work;
import com.leoman.work.entity.WorkComic;
import com.sun.net.httpserver.Authenticator;
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
@RequestMapping(value = "admin/still")
public class StillController extends GenericEntityController<StillResource, StillResource, StillServiceImpl> {

    @Autowired
    private StillService stillService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "resource/listStill";
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

            Page<StillResource> page = stillService.page(pageNum, length);

            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
            WebUtil.print(response, emptyData);
        }
    }

    /**
     * 剧照类别
     *
     * @return
     */
    @RequestMapping(value = "/categoryList")
    @ResponseBody
    public List<Category> categoryList() {

        List<Category> categoryList = categoryService.findType(2);
        return categoryList;
    }

    /**
     * 删除剧照
     *
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Integer delete(Long stillId) {

        StillResource stillResource = stillService.queryByPK(stillId);
        stillService.delete(stillResource);

        return 1;
    }

    /**
     * 批量删除剧照
     *
     * @return
     */
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ResponseBody
    public Integer deleteBatch(String ids) {
        Long[] arrayId = JsonUtil.json2Obj(ids, Long[].class);
        for (Long id : arrayId) {
            StillResource stillResource = stillService.queryByPK(id);
            stillService.delete(stillResource);
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
                        @RequestParam(value = "imageFile",required = false) MultipartFile file) throws IOException {

        StillResource stillResource = new StillResource();
        Category category = categoryService.queryByPK(categoryId);
        category.setCount(category.getCount() + 1);
        categoryService.update(category);
        stillResource.setCategory(category);

        MultipartFile multipartFile = file;
        if (null != multipartFile) {
            FileBo fileBo = FileUtil.save(multipartFile);
            stillResource.setImageUrl(fileBo.getPath());
        }

        stillService.insert(stillResource);
        return 1;
    }
}
