package com.leoman.post.controller;

import com.leoman.category.entity.Category;
import com.leoman.category.service.CategoryService;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.entity.FileBo;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.post.entity.PostBase;
import com.leoman.post.entity.PostImage;
import com.leoman.post.entity.TpPostSub;
import com.leoman.post.entity.ZbPostInfo;
import com.leoman.post.service.PostBaseService;
import com.leoman.post.service.PostImageService;
import com.leoman.post.service.TpPostSubService;
import com.leoman.post.service.ZbPostInfoService;
import com.leoman.post.service.impl.PostBaseServiceImpl;
import com.leoman.utils.DateUtils;
import com.leoman.utils.FileUtil;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.WebUtil;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

;

/**
 * Created by Administrator on 2016/7/13 0013.
 */
@Controller
@RequestMapping(value = "admin/post")
public class PostController extends GenericEntityController<PostBase, PostBase, PostBaseServiceImpl> {


    @Autowired
    private PostBaseService postBaseService;

    @Autowired
    private ZbPostInfoService zbPostInfoService;

    @Autowired
    private TpPostSubService tpPostSubService;

    @Autowired
    private CategoryService categoryService;

    /*@Autowired
    private UserIntegralService userIntegralService;*/

    @Autowired
    private PostImageService postImageService;
    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "post/list";
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     String name,
                     Long category,
                     Integer status,
                     Integer draw,
                     Integer start,
                     Integer length) {
        try {

            int pageNum = getPageNum(start, length);

            Page<PostBase> page = postBaseService.findPage(name, category, status, null, pageNum, length);
            for (PostBase postBase : page.getContent()) {
                if (postBase.getType() == 1) {
                    if (postBase.getStartDate() <= System.currentTimeMillis() && postBase.getStatus() != 2) {
                        postBase.setStatus(1);
                    }
                }
                if (postBase.getType() == 2) {
                    if (postBase.getEndDate() < System.currentTimeMillis()) {
                        postBase.setStatus(2);
                    }else {
                        postBase.setStatus(1);
                    }
                }
                postBaseService.update(postBase);
            }

            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
            WebUtil.print(response, emptyData);
        }
    }

    /**
     * 上传缓存图片
     *
     * @return
     */
    @RequestMapping("/addTempImage")
    @ResponseBody
    public PostImage addTempImage(MultipartRequest multipartRequest, Long zbPostId) {
        PostImage image = new PostImage();

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
            image.setType(1);
            if (zbPostId != null) {
                image.setPostId(zbPostId);
            }

            postImageService.save(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    public String add() {

        return "post/add";
    }

    /**
     * 编辑
     */
    @RequestMapping(value = "/edit")
    public String edit(Long postId, Model model) {

        PostBase postBase = postBaseService.queryByPK(postId);
        model.addAttribute("postBase",postBase);
        if (postBase.getType() == 0) {
            return "post/ptPostAdd";
        }else if (postBase.getType() == 1) {
            List<ZbPostInfo> zbPostInfoList = zbPostInfoService.findByPostId(postId);
            for (ZbPostInfo zbPostInfo : zbPostInfoList) {
                zbPostInfo.setImageList(postImageService.findList(zbPostInfo.getId(), 1));
            }
            model.addAttribute("zbPostInfoList",zbPostInfoList);
            return "post/zbPostAdd";
        }else if (postBase.getType() == 2) {
            List<TpPostSub> tpPostSubList = tpPostSubService.findByPostId(postId);
            model.addAttribute("tpPostSubList",tpPostSubList);
            return "post/tpPostAdd";
        }
        return null;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail")
    public String detail(Long postId, Model model) {

        PostBase postBase = postBaseService.queryByPK(postId);
        model.addAttribute("postBase",postBase);
        if (postBase.getType() == 0) {

            return "post/ptPostDetail";
        }else if (postBase.getType() == 1) {
            List<ZbPostInfo> zbPostInfoList = zbPostInfoService.findByPostId(postId);
            for (ZbPostInfo zbPostInfo : zbPostInfoList) {
                zbPostInfo.setImageList(postImageService.findList(zbPostInfo.getId(), 1));
            }
            model.addAttribute("zbPostInfoList",zbPostInfoList);
            return "post/zbPostDetail";
        }else if (postBase.getType() == 2) {
            List<TpPostSub> tpPostSubList = tpPostSubService.findByPostId(postId);
            model.addAttribute("tpPostSubList",tpPostSubList);
            return "post/tpPostDetail";
        }

        return null;
    }

    /**
     * 设置、取消精华
     */
    @RequestMapping(value = "/isEssence")
    @ResponseBody
    public Integer isEssence(Long postId, Integer essence) {

        PostBase postBase = postBaseService.queryByPK(postId);
        postBase.setEssence(essence);

        if (essence == 1) {
            // userIntegralService.changeIntegral(postBase.getUserInfo(), "帖子被加精华", 50);
        }

        postBaseService.update(postBase);

        return 1;
    }

    /**
     * 设置、取消置顶
     */
    @RequestMapping(value = "/isStick")
    @ResponseBody
    public Integer isStick(Long postId, Integer stick) {

        PostBase postBase = postBaseService.queryByPK(postId);
        postBase.setStick(stick);

        if (stick == 1) {
            // userIntegralService.changeIntegral(postBase.getUserInfo(), "帖子被置顶", 50);
        }

        postBaseService.update(postBase);

        return 1;
    }

    /**
     * 删除直播贴图片
     */
    @RequestMapping(value = "/deleteImage")
    @ResponseBody
    public Integer deleteImage(Long imageId) {

        PostImage postImage = postImageService.queryByPK(imageId);
        postImageService.delete(postImage);

        return 1;
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Integer save(Long postId,
                        String name,
                        Long typeList,
                        Integer type,
                        String content,
                        String startDate,
                        String description,
                        String endDate,
                        String[] tpSub,
                        String[] tpSubs,
                        MultipartRequest multipartRequest) throws IOException, ParseException {


        PostBase postBase = null;
        Category category = null;
        if (postId == null) {
            postBase = new PostBase();
            postBase.setCreateDate(System.currentTimeMillis());
        }else {
            postBase = postBaseService.queryByPK(postId);
            category = postBase.getCategory();
            category.setCount(category.getCount() - 1);
            categoryService.update(category);
        }

        MultipartFile multipartFile = multipartRequest.getFile("cover");
        if (null != multipartFile) {
            FileBo fileBo = FileUtil.save(multipartFile);
            postBase.setAvater(fileBo.getPath());
        }

        postBase.setName(name);
        postBase.setType(type);
        category = categoryService.queryByPK(typeList);
        category.setCount(category.getCount() + 1);
        categoryService.update(category);
        postBase.setCategory(category);
        postBase.setUpdateDate(System.currentTimeMillis());

        if (type == 0) {
            postBase.setContent(content);
            postBase.setStatus(3);
            postBaseService.save(postBase);
        }else if (type == 1) {
            postBase.setStatus(0);
            postBase.setStartDate(DateUtils.stringToLong(startDate, "yyyy-MM-dd hh:mm:ss"));
            postBaseService.save(postBase);
        }else if (type == 2) {
            postBase.setContent(description);
            postBase.setStatus(1);
            postBase.setEndDate(DateUtils.stringToLong(endDate, "yyyy-MM-dd hh:mm:ss"));
            postBaseService.save(postBase);
            //修改投票对象
            List<TpPostSub> tpPostSubList = tpPostSubService.findByPostId(postBase.getId());
            if (tpPostSubList.size() > 0) {
                for (int i = 0; i < tpSubs.length; i++) {
                    if (StringUtils.isNotEmpty(tpSubs[i])) {
                        tpPostSubList.get(i).setName(tpSubs[i]);
                        tpPostSubList.get(i).setPostBase(postBase);
                        MultipartFile file = multipartRequest.getFile("avaters" + tpPostSubList.get(i).getId());
                        if (null != file) {
                            FileBo fileBo = FileUtil.save(file);
                            tpPostSubList.get(i).setCover(fileBo.getPath());
                        }
                        /*List<MultipartFile> multipartFiles = multipartRequest.getFiles("avaters");
                        if (multipartFiles.size() > 0) {
                            FileBo fileBo = FileUtil.save(multipartFiles.get(i));
                            tpPostSubList.get(i).setCover(fileBo.getPath());
                        }*/
                        tpPostSubService.save(tpPostSubList.get(i));
                    }
                }
            }

            //新增投票对象
            for (int i = 0; i < tpSub.length; i++) {
                if (StringUtils.isNotEmpty(tpSub[i])) {
                    TpPostSub tpPostSub = new TpPostSub();
                    tpPostSub.setName(tpSub[i]);
                    tpPostSub.setCount(0);
                    tpPostSub.setPostBase(postBase);
                    List<MultipartFile> multipartFiles = multipartRequest.getFiles("avater");
                    if (multipartFiles.size() > 0) {
                        FileBo fileBo = FileUtil.save(multipartFiles.get(i));
                        tpPostSub.setCover(fileBo.getPath());
                    }
                    tpPostSubService.save(tpPostSub);
                }
            }
        }
        //postBaseService.save(postBase);
        return 1;
    }

    /**
     * 添加直播
     */
    @RequestMapping(value = "/addZB")
    @ResponseBody
    public Integer addZB(HttpServletRequest request,
                         Long postId,
                         String tempAddImageIds1,
                         String tempDelImageIds,
                         Integer status) throws UnsupportedEncodingException {

        PostBase postBase = postBaseService.queryByPK(postId);
        if (status == null) {
            postBase.setStatus(postBase.getStatus());
        }else {
            postBase.setStatus(2);
        }
        postBase.setUpdateDate(System.currentTimeMillis());
        postBaseService.update(postBase);

        String msg = request.getParameter("content");
        String content = new String(msg.getBytes("ISO-8859-1"), "UTF-8");

        ZbPostInfo zbPostInfo = new ZbPostInfo();
        zbPostInfo.setPostBase(postBase);
        zbPostInfo.setContent(content);
        zbPostInfoService.save(zbPostInfo);

        String[] addImageIds1 = null;
        if (tempAddImageIds1 != null && tempAddImageIds1 != "") {
            addImageIds1 = tempAddImageIds1.split(",");

            // 保存图片集合
            PostImage image = null;
            for (String imageId : addImageIds1) {
                if (null != imageId && !imageId.equals("")) {
                    image = postImageService.queryByPK(Long.parseLong(imageId));
                    //image.setImage();
                    image.setType(1);
                    image.setPostId(zbPostInfo.getId());

                    postImageService.update(image);
                }
            }
        }

        String[] delImageIds1 = null;
        if (tempDelImageIds != null && tempDelImageIds != "") {
            delImageIds1 = tempDelImageIds.split(",");

            // 删除图片集合
            PostImage image = null;
            for (String imageId : delImageIds1) {
                if (null != imageId && !imageId.equals("")) {
                    image = postImageService.queryByPK(Long.parseLong(imageId));

                    postImageService.delete(image);
                }
            }
        }

        return 1;
    }

    /**
     * 保存修改后的直播贴
     */
    @RequestMapping(value = "/saveZB")
    @ResponseBody
    public Integer saveZB(HttpServletRequest request,
                          Long postId,
                          String name,
                          Long typeList,
                          Integer type,
                          String startDate,
                          String[] description,
                          String tempDelImageIds,
                          MultipartRequest multipartRequest) throws IOException, ParseException {

        PostBase postBase = postBaseService.queryByPK(postId);
        MultipartFile multipartFile = multipartRequest.getFile("cover");
        if (null != multipartFile) {
            FileBo fileBo = FileUtil.save(multipartFile);
            postBase.setAvater(fileBo.getPath());
        }
        postBase.setName(name);
        // 先删除原有的帖子分类数量
        Category category = postBase.getCategory();
        category.setCount(category.getCount() - 1);
        categoryService.update(category);

        // 后新增帖子分类数量
        Category category1 = categoryService.queryByPK(typeList);
        category1.setCount(category1.getCount() + 1);
        categoryService.update(category1);
        postBase.setCategory(category1);
        postBase.setType(type);
        postBase.setStartDate(DateUtils.stringToLong(startDate, "yyyy-MM-dd hh:mm:ss"));
        postBaseService.update(postBase);

        List<ZbPostInfo> zbPostInfoList = zbPostInfoService.findByPostId(postId);
        if (zbPostInfoList.size() > 0) {
            for (int i = 0; i < description.length; i++) {
                if (StringUtils.isNotEmpty(description[i])) {
                    Long id = zbPostInfoList.get(i).getId();
                    zbPostInfoList.get(i).setContent(description[i]);
                    zbPostInfoList.get(i).setPostBase(postBase);

                    zbPostInfoService.save(zbPostInfoList.get(i));
                }
            }
        }

        String[] addImageIds1 = null;
        if (tempDelImageIds != null && tempDelImageIds != "") {
            addImageIds1 = tempDelImageIds.split(",");
            // 保存图片集合
            PostImage image = null;
            for (String imageId : addImageIds1) {
                if (null != imageId && !imageId.equals("")) {
                    image = postImageService.queryByPK(Long.parseLong(imageId));
                    postImageService.delete(image);
                }
            }
        }
        return 1;
    }

    /**
     * 批量删除帖子
     */
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ResponseBody
    public Integer deleteBatch(String ids) {
        Long[] arrayId = JsonUtil.json2Obj(ids, Long[].class);
        for (Long id : arrayId) {
            PostBase postBase = postBaseService.queryByPK(id);
            postBaseService.delete(postBase);
        }
        return 1;
    }

    /**
     * 单个删除帖子
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Integer delete(Long postId) {

        PostBase postBase = postBaseService.queryByPK(postId);
        postBaseService.delete(postBase);

        return 1;
    }

    /**
     * 删除投票对象
     */
    @RequestMapping(value = "/deleteTpSub")
    @ResponseBody
    public Integer deleteTpSub(Long postId) {

        TpPostSub tpPostSub = tpPostSubService.queryByPK(postId);
        tpPostSubService.delete(tpPostSub);

        return 1;
    }
}
