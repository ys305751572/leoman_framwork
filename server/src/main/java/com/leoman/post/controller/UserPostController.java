package com.leoman.post.controller;

import com.leoman.category.entity.Category;
import com.leoman.category.service.CategoryService;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.entity.FileBo;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.post.entity.PostBase;
import com.leoman.post.entity.PostImage;
import com.leoman.post.service.PostBaseService;
import com.leoman.post.service.PostImageService;
import com.leoman.post.service.impl.PostBaseServiceImpl;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.service.UserService;
import com.leoman.utils.FileUtil;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.WebUtil;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/27 0027.
 */
@Controller
@RequestMapping(value = "admin/userPost")
public class UserPostController extends GenericEntityController<PostBase, PostBase, PostBaseServiceImpl> {

    @Autowired
    private PostBaseService postBaseService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostImageService postImageService;

    /*@Autowired
    private UserIntegralService userIntegralService;*/

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "post/userPostList";
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     String content,
                     String mobile,
                     Long category,
                     Integer status,
                     Integer draw,
                     Integer start,
                     Integer length) {
        try {

            int pageNum = getPageNum(start, length);

            Page<PostBase> page = postBaseService.page(content, mobile, category, status, 3, pageNum, length);

            /*List<PostBase> list = page.getContent();
            for (PostBase postBase: list) {
                UserPost userPost = userPostService.iFindOne(postBase.getPostId());
                //UserPost userPost = userPostService.findById(postBase.getPostId());
                postBase.setUserPost(userPost);
            }*/
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
    @RequestMapping(value = "/add")
    public String add() {

        return "post/userPostAdd";
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail")
    public String detail(Long postId, Model model) {

        PostBase postBase = postBaseService.queryByPK(postId);
        List<PostImage> postImageList = postImageService.findList(postId, 3);

        /*List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        for (PostImage postImage : postImageList) {
            map = new HashMap<String, Object>();
            map.put("id", postImage.getId());
            map.put("path", postImage.getUrl());

            list.add(map);
        }*/

        model.addAttribute("postBase", postBase);
        model.addAttribute("postImageList", postImageList);

        return "post/userPostDetail";
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
     * 查询帖子分类列表
     */
    @RequestMapping(value = "/categoryList")
    @ResponseBody
    public List<Category> categoryList() {

        List<Category> categoryList = categoryService.findByType(1);

        return categoryList;
    }

    /**
     * 上传缓存图片
     *
     * @return
     */
    @RequestMapping("/addTempImage")
    @ResponseBody
    public PostImage addTempImage(MultipartRequest multipartRequest) {
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
            image.setType(3);

            postImageService.save(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;
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
            //UserPost userPost = userPostService.queryByPK(postBase.getPostId());
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
            //UserPost userPost = userPostService.queryByPK(postBase.getPostId());
            // userIntegralService.changeIntegral(postBase.getUserInfo(), "帖子被置顶", 50);
        }

        postBaseService.update(postBase);

        return 1;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Integer delete(Long postId) {

        PostBase postBase = postBaseService.queryByPK(postId);
        postBase.setStatus(1);
        postBaseService.update(postBase);

        return 1;
    }

    /**
     * 批量删除
     */
    @RequestMapping("/deleteBatch")
    @ResponseBody
    public Integer deleteBatch(HttpServletRequest request, String ids) {
        try {
            // 将界面上的id数组格式的字符串解析成int类型的数组
            Long[] arrayId = JsonUtil.json2Obj(ids, Long[].class);
            for (Long id : arrayId) {
                PostBase postBase = postBaseService.queryByPK(id);
                postBase.setStatus(1);
                postBaseService.update(postBase);
                //postBaseService.delete(postBase);
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Integer save(Long creatorId,
                        Long category,
                        String description,
                        String tempAddImageIds1,
                        String tempDelImageIds) {

        String[] addImageIds1 = null;

        PostBase postBase = new PostBase();
        postBase.setType(3);
        postBase.setStatus(0);

        // 新帖子分类数量
        Category category1 = categoryService.queryByPK(category);
        category1.setCount(category1.getCount() + 1);
        categoryService.update(category1);
        postBase.setCategory(category1);

        postBase.setUserInfo(userService.queryByPK(creatorId));
        postBase.setContent(description);
        postBase.setCreateDate(System.currentTimeMillis());
        postBase.setUpdateDate(System.currentTimeMillis());
        postBaseService.save(postBase);

        if (tempAddImageIds1 != null && tempAddImageIds1 != "") {
            addImageIds1 = tempAddImageIds1.split(",");
            // 保存图片集合
            PostImage image = null;
            for (String imageId : addImageIds1) {
                if (null != imageId && !imageId.equals("")) {
                    image = postImageService.queryByPK(Long.parseLong(imageId));
                    //image.setImage();
                    image.setType(3);
                    image.setPostId(postBase.getId());

                    postImageService.update(image);
                }
            }
        }

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

        postBaseService.update(postBase);

        return 1;
    }
}
