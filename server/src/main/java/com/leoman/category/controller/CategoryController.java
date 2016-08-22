package com.leoman.category.controller;

import com.leoman.category.service.impl.CategoryServiceImpl;
import com.leoman.category.entity.Category;
import com.leoman.category.service.CategoryService;
import com.leoman.comment.entity.Comment;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.entity.FileBo;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.user.entity.UserInfo;
import com.leoman.utils.FileUtil;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.WebUtil;
import com.leoman.work.entity.WorkVideo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/24 0024.
 */
@Controller
@RequestMapping(value = "admin/category")
public class CategoryController extends GenericEntityController<Category, Category, CategoryServiceImpl> {

    @Autowired
    private CategoryService categoryService;

    /**
     * 作品集分类列表页面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "category/list";
    }

    /**
     * 查询作品集分类列表
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                         HttpServletResponse response,
                         Integer draw,
                         Integer start,
                         Integer length) {
        try {

            int pageNum = getPageNum(start, length);

            Page<Category> page = categoryService.page(null, pageNum, length);

            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
            WebUtil.print(response, emptyData);
        }
    }

    /**
     * 编辑保存作品集分类
     *
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    private Integer edit(HttpServletRequest request, Long categoryId, @RequestParam(value = "imageFile",required = false) MultipartFile file)  {

        Category category = null;
        String msg = request.getParameter("name");
        //String name = new String(msg.getBytes("ISO-8859-1"), "UTF-8");

        MultipartFile multipartFile = file;
        if (null != multipartFile) {
            FileBo fileBo = null;
            try {
                fileBo = FileUtil.save(multipartFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (categoryId == null) {
                category = new Category();
                category.setType(4);
                category.setName(msg + "视频");
                category.setUrl(fileBo.getPath());
                category.setCreateDate(System.currentTimeMillis());
                category.setUpdateDate(System.currentTimeMillis());
                categoryService.save(category);

                category = new Category();
                category.setType(5);
                category.setName(msg + "小说");
                category.setUrl(fileBo.getPath());
                category.setCreateDate(System.currentTimeMillis());
                category.setUpdateDate(System.currentTimeMillis());
                categoryService.save(category);

                category = new Category();
                category.setType(6);
                category.setName(msg + "漫画");
                category.setUrl(fileBo.getPath());
                category.setCreateDate(System.currentTimeMillis());
                category.setUpdateDate(System.currentTimeMillis());
                categoryService.save(category);
            }else {
                category = categoryService.queryByPK(categoryId);
                category.setName(msg);
                category.setUrl(fileBo.getPath());
                category.setUpdateDate(System.currentTimeMillis());
                categoryService.save(category);
            }
        }

        return 1;
    }

    /**
     * 帖子分类列表页面
     *
     * @return
     */
    @RequestMapping(value = "/indexPost")
    public String indexPost() {
        return "category/listPost";
    }

    /**
     * 查询帖子分类列表
     */
    @RequestMapping(value = "/listPost")
    public void listPost(HttpServletRequest request,
                     HttpServletResponse response,
                     Integer draw,
                     Integer start,
                     Integer length) {
        try {

            int pageNum = getPageNum(start, length);

            Page<Category> page = categoryService.page(1, pageNum, length);

            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
            WebUtil.print(response, emptyData);
        }
    }

    /**
     * 编辑保存帖子分类
     *
     * @return
     */
    @RequestMapping(value = "/editPost")
    @ResponseBody
    private Integer editPost(HttpServletRequest request, Long categoryId) throws UnsupportedEncodingException {

        Category category = null;
        String msg = request.getParameter("name");
        String name = new String(msg.getBytes("ISO-8859-1"), "UTF-8");

        if (categoryId == null) {
            category = new Category();
            category.setCreateDate(System.currentTimeMillis());
        }else {
            category = categoryService.queryByPK(categoryId);
        }
        category.setName(name);
        category.setType(1);
        category.setUpdateDate(System.currentTimeMillis());
        categoryService.save(category);

        return 1;
    }

    /**
     * 帖子下架、删除剧照音乐
     *
     * @return
     */
    @RequestMapping(value = "isDown")
    @ResponseBody
    private Integer isDown(Long categoryId, Integer status) {

        Category category = categoryService.queryByPK(categoryId);
        category.setStatus(status);
        categoryService.save(category);
        categoryService.update(category);

        return 1;
    }

    /**
     * 剧照分类列表页面
     *
     * @return
     */
    @RequestMapping(value = "/indexStill")
    public String indexStill() {
        return "category/listStill";
    }

    /**
     * 查询剧照分类列表
     */
    @RequestMapping(value = "/listStill")
    public void listStill(HttpServletRequest request,
                         HttpServletResponse response,
                         Integer draw,
                         Integer start,
                         Integer length) {
        try {

            int pageNum = getPageNum(start, length);

            Page<Category> page = categoryService.findPage(2, 0, pageNum, length);

            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
            WebUtil.print(response, emptyData);
        }
    }

    /**
     * 编辑保存剧照分类
     *
     * @return
     */
    @RequestMapping(value = "/editStill")
    @ResponseBody
    public Integer editStill(HttpServletRequest request, Long categoryId, @RequestParam(value = "imageFile",required = false) MultipartFile file) throws IOException {

        Category category = null;
        String msg = request.getParameter("name");
        //String name = new String(msg.getBytes("ISO-8859-1"), "UTF-8");

        if (categoryId == null) {
            category = new Category();
            category.setCreateDate(System.currentTimeMillis());
        }else {
            category = categoryService.queryByPK(categoryId);
        }
        category.setName(msg);
        category.setType(2);

        MultipartFile multipartFile = file;
        if (null != multipartFile) {
            FileBo fileBo = FileUtil.save(multipartFile);
            category.setUrl(fileBo.getPath());
        }

        category.setUpdateDate(System.currentTimeMillis());
        categoryService.save(category);

        return 1;
    }

    /**
     * 音乐分类列表页面
     *
     * @return
     */
    @RequestMapping(value = "/indexMusic")
    public String indexMusic() {
        return "category/listMusic";
    }

    /**
     * 查询音乐分类列表
     */
    @RequestMapping(value = "/listMusic")
    public void listMusic(HttpServletRequest request,
                         HttpServletResponse response,
                         Integer draw,
                         Integer start,
                         Integer length) {
        try {

            int pageNum = getPageNum(start, length);

            Page<Category> page = categoryService.findPage(3, 0, pageNum, length);

            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
            WebUtil.print(response, emptyData);
        }
    }

    /**
     * 编辑保存音乐分类
     *
     * @return
     */
    @RequestMapping(value = "/editMusic")
    @ResponseBody
    public Integer editMusic(HttpServletRequest request, Long categoryId, @RequestParam(value = "imageFile",required = false) MultipartFile file) throws IOException {

        Category category = null;
        String msg = request.getParameter("name");
        //String name = new String(msg.getBytes("ISO-8859-1"), "UTF-8");

        if (categoryId == null) {
            category = new Category();
            category.setCreateDate(System.currentTimeMillis());
        }else {
            category = categoryService.queryByPK(categoryId);
        }
        category.setName(msg);
        category.setType(3);

        MultipartFile multipartFile = file;
        if (null != multipartFile) {
            FileBo fileBo = FileUtil.save(multipartFile);
            category.setUrl(fileBo.getPath());
        }

        category.setUpdateDate(System.currentTimeMillis());
        categoryService.save(category);

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
            Category category = categoryService.queryByPK(id);
            category.setStatus(1);
            categoryService.save(category);
            categoryService.update(category);
            //categoryService.delete(categoryService.queryByPK(id));
        }
        return 1;
    }
}
