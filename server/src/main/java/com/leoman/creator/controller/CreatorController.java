package com.leoman.creator.controller;

import com.leoman.address.entity.Address;
import com.leoman.address.service.AddressService;
import com.leoman.city.entity.City;
import com.leoman.city.service.CityService;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.ErrorType;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.creator.entity.CreatorImage;
import com.leoman.creator.service.CreatorImageService;
import com.leoman.creator.service.CreatorService;
import com.leoman.creator.service.impl.CreatorServiceImpl;
import com.leoman.creator.entity.Creator;
import com.leoman.creatordynamic.entity.Dynamic;
import com.leoman.dynamic.service.DynamicService;
import com.leoman.entity.FileBo;
import com.leoman.exception.GeneralExceptionHandler;
import com.leoman.province.entity.Province;
import com.leoman.province.service.ProvinceService;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.service.UserService;
import com.leoman.utils.*;
import com.leoman.vcode.VcodeService;
import com.leoman.work.entity.WorkCreator;
import com.leoman.work.service.WorkCreatorService;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.spi.ErrorCode;
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
import java.awt.event.ActionEvent;
import java.util.*;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
@Controller
@RequestMapping(value = "admin/creator")
public class CreatorController extends GenericEntityController<Creator, Creator, CreatorServiceImpl> {

    @Autowired
    private CreatorService creatorService;

    @Autowired
    private CreatorImageService creatorImageService;

    @Autowired
    private WorkCreatorService workCreatorService;

    @Autowired
    private UserService userService;

    @Autowired
    private DynamicService dynamicService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private VcodeService vcodeService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "creator/list";
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     String mobile,
                     String nickname,
                     Integer gender,
                     Integer status,
                     Integer draw,
                     Integer start,
                     Integer length) {
        try {
            int pageNum = getPageNum(start, length);

            Page<UserInfo> page = userService.page(mobile, nickname, gender, status, 1, pageNum, length);
            List<UserInfo> list = page.getContent();
            for (UserInfo userInfo : list) {
                List<WorkCreator> workCreatorList = workCreatorService.findByUserId(userInfo.getId());
                if (workCreatorList == null) {
                    userInfo.setNum(0);
                }
                userInfo.setNum(workCreatorList.size());
            }
            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            GeneralExceptionHandler.log(e);
            WebUtil.print(response, emptyData);
        }
    }

    /**
     * 封禁
     *
     * @param response
     * @param userId
     * @return
     */
    @RequestMapping(value = "/banned")
    @ResponseBody
    public Integer banned(HttpServletResponse response, Long userId, Integer status) {

        UserInfo userInfo = userService.queryByPK(userId);
        userInfo.setStatus(status);
        userService.update(userInfo);

        return 1;
    }

    /**
     * 推荐到艺人区
     *
     * @param response
     * @param userId
     * @return
     */
    @RequestMapping(value = "/setCreator")
    @ResponseBody
    public Integer setCreator(HttpServletResponse response, Long userId, Integer status) {

        UserInfo userInfo = userService.queryByPK(userId);
        Creator creator = userInfo.getCreator();
        creator.setStatus(status);

        creatorService.update(creator);

        return 1;
    }

    /**
     * 详情
     *
     * @param userId
     * @param model
     */
    @RequestMapping(value = "/detail")
    public String detail(Long userId, Model model) {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        UserInfo userInfo = userService.queryByPK(userId);

        Map<String, Object> map = new HashMap<String, Object>();
        /*map.put("id", userInfo.getId());
        map.put("path", userInfo.getAvater());

        map.put("id", userInfo.getCreator().getId());
        map.put("path", userInfo.getCreator().getCoverUrl());*/

        //List<Dynamic> list1 = dynamicService.findByCreatorId(userInfo.getCreator().getId());
        List<Dynamic> dynamicList = dynamicService.findListNew(userInfo.getCreator().getId());
        model.addAttribute("dynamicList", dynamicList);

        Creator creator = userInfo.getCreator();
        List<CreatorImage> creatorImageList = creatorImageService.findByCreatorId(creator.getId());
        for (CreatorImage creatorImage : creatorImageList) {
            map = new HashMap<String, Object>();
            map.put("id", creatorImage.getId());
            map.put("type", creatorImage.getType());
            map.put("path", creatorImage.getImage());

            list.add(map);
        }

        List<Address> addressList = addressService.findByUserId(userInfo.getId());

        List<WorkCreator> workCreatorList = workCreatorService.findByUserId(userId);
        model.addAttribute("workCreatorList", workCreatorList);

        model.addAttribute("userInfo", userInfo);
        model.addAttribute("addressList", addressList);
        model.addAttribute("creatorImageList", JSONArray.fromObject(list));

        return "creator/detail";
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {
        model.addAttribute("creatorImageList", JSONArray.fromObject(new ArrayList<Map<String, Object>>()));
        return "creator/add";
    }

    /**
     * 编辑
     *
     * @param userId
     * @param model
     */
    @RequestMapping(value = "/edit")
    public String edit(Long userId, Model model) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

            UserInfo userInfo = userService.queryByPK(userId);

            List<CreatorImage> creatorImageList = creatorImageService.findByCreatorId(userInfo.getCreator().getId());
            for (CreatorImage creatorImage : creatorImageList) {
                map = new HashMap<String, Object>();
                map.put("id", creatorImage.getId());
                map.put("type", creatorImage.getType());
                map.put("path", creatorImage.getImage());

                list.add(map);
            }

            List<Dynamic> dynamicList = dynamicService.findByCreatorId(userInfo.getCreator().getId());

            model.addAttribute("userInfo", userInfo);
            model.addAttribute("dynamicList", dynamicList);
            model.addAttribute("creatorImageList", JSONArray.fromObject(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "creator/add";
    }

    /**
     * 查询省份列表
     */
    @RequestMapping(value = "/provinceList")
    @ResponseBody
    public List<Province> provinceList() {

        List<Province> provinceList = provinceService.iFindList();

        return provinceList;
    }

    /**
     * 查询城市列表
     */
    @RequestMapping(value = "/cityList")
    @ResponseBody
    public List<City> cityList(Long provinceId) {

        List<City> cityList = cityService.findById(provinceId);

        return cityList;
    }

    /**
     * 上传缓存图片
     *
     * @return
     */
    @RequestMapping("/addTempImage")
    @ResponseBody
    public CreatorImage addTempImage(MultipartRequest multipartRequest, Integer type) {
        CreatorImage image = new CreatorImage();

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
            image.setImage(fileBo.getPath());
            image.setType(type);

            creatorImageService.save(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Integer save(Long id,
                        String mobile,
                        String nickname,
                        String password,
                        Integer gender,
                        Long provinceId,
                        Long cityId,
                        String description,
                        String weibo,
                        String experience,
                        String[] time,
                        Integer[] thing,
                        String tempAddImageIds1,
                        String tempAddImageIds2,
                        String tempDelImageIds,
                        String[] times,
                        Integer[] things,
                        MultipartRequest multipartRequest) {

        try {

            /*String[] addImageIds1 = tempAddImageIds1.split(",");
            String[] addImageIds2 = tempAddImageIds2.split(",");
            String[] delImageIds = tempDelImageIds.split(",");

            UserInfo userInfo = null;
            Creator creator = null;
            List<UserInfo> userInfoList = userService.queryAll();
            if (null == id) {
                // 判断昵称是否已存在
                for (UserInfo userInfo1 : userInfoList) {
                    if (nickname.equals(userInfo1.getNickname())) {
                        return 2;
                    }
                }
                userInfo = new UserInfo();
                userInfo.setCreateDate(System.currentTimeMillis());
            } else {
                userInfo = userService.queryByPK(id);
                // 判断昵称是否已存在
                for (UserInfo userInfo1 : userInfoList) {
                    if (nickname.equals(userInfo1.getNickname()) && !nickname.equals(userInfo.getNickname())) {
                        return 2;
                    }
                }
            }

            if (userInfo.getCreator() == null) {
                creator = new Creator();
                creator.setCreateDate(System.currentTimeMillis());
            } else {
                creator = userInfo.getCreator();
            }

            creator.setProvince(provinceService.queryByPK(provinceId));
            creator.setCity(cityService.queryByPK(cityId));
            creator.setDescription(description);
            creator.setWeibo(weibo);
            creator.setExperience(experience);

            MultipartFile multipartFile2 = multipartRequest.getFile("imageFile2");
            if (null != multipartFile2) {
                FileBo fileBo = FileUtil.save(multipartFile2);
                creator.setCoverUrl(fileBo.getPath());
            }
            MultipartFile multipartFile1 = multipartRequest.getFile("url");
            if (null != multipartFile1) {
                FileBo fileBo = FileUtil.save(multipartFile1);
                creator.setAudioUrl(fileBo.getPath());
            }
            creatorService.save(creator);

            userInfo.setNickname(nickname);
            userInfo.setMobile(mobile);
            userInfo.setPassword(password);
            userInfo.setGender(gender);

            MultipartFile multipartFile = multipartRequest.getFile("imageFile");
            if (null != multipartFile) {
                FileBo fileBo = FileUtil.save(multipartFile);
                userInfo.setAvater(fileBo.getPath());
            }
            userInfo.setCreator(creator);
            userInfo.setIsCreator(1);
            userService.save(userInfo);

            //修改动态保存
            List<Dynamic> dynamicList = dynamicService.findByCreatorId(creator.getId());
            if (dynamicList.size() > 0) {
                for (int i = 0; i < times.length; i++) {
                    if (StringUtils.isNotEmpty(times[i])) {
                        dynamicList.get(i).setTime(DateUtils.stringToLong(times[i], "yyyy-MM-dd hh:mm:ss"));
                        dynamicList.get(i).setDate(times[i].substring(0, 11));
                        dynamicList.get(i).setCreator(creator);
                        if (StringUtils.isNotEmpty(things[i])) {
                            dynamicList.get(i).setThing(things[i]);
                        }
                        dynamicService.update(dynamicList.get(i));
                    }
                }
            }

            //新增动态
            for (int i = 0; i < time.length; i++) {
                if (StringUtils.isNotEmpty(time[i])) {
                    Dynamic dynamic = new Dynamic();
                    dynamic.setTime(DateUtils.stringToLong(time[i], "yyyy-MM-dd hh:mm:ss"));
                    dynamic.setDate(time[i].substring(0, 11));
                    dynamic.setCreator(creator);
                    if (StringUtils.isNotEmpty(thing[i])) {
                        dynamic.setThing(thing[i]);
                    }
                    dynamicService.save(dynamic);
                }
            }

            // 保存剧照图片集合
            CreatorImage image = null;
            for (String imageId : addImageIds1) {
                if (null != imageId && !imageId.equals("")) {
                    image = creatorImageService.queryByPK(Long.parseLong(imageId));
                    //image.setImage();
                    image.setType(0);
                    image.setCreator(creator);

                    creatorImageService.update(image);
                }
            }

            // 保存生活照图片集合
            CreatorImage image2 = null;
            for (String imageId : addImageIds2) {
                if (null != imageId && !imageId.equals("")) {
                    image2 = creatorImageService.queryByPK(Long.parseLong(imageId));
                    //image2.setImage();
                    image2.setType(1);
                    image2.setCreator(creator);

                    creatorImageService.update(image2);
                }
            }

            // 删除图片集合
            CreatorImage image3 = null;
            for (String imageId : delImageIds) {
                if (null != imageId && !imageId.equals("")) {
                    image3 = creatorImageService.queryByPK(Long.parseLong(imageId));
                    creatorImageService.delete(image3);
                }
            }*/
            Integer stataus = creatorService.saveAll(id, nickname, password, gender, provinceId, cityId, description, weibo, experience, time, thing,
                    tempAddImageIds1, tempAddImageIds2, tempDelImageIds, times, things, multipartRequest);
            return stataus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ResponseBody
    public Integer deleteBatch(String ids) {
        Long[] arrayId = JsonUtil.json2Obj(ids, Long[].class);
        for (Long id : arrayId) {
            UserInfo userInfo = userService.queryByPK(id);
            userInfo.setStatus(1);
            userService.save(userInfo);
            userService.update(userInfo);
            //categoryService.delete(categoryService.queryByPK(id));
        }
        return 1;
    }

    /**
     * 删除动态
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Integer delete(Long id) {

        Dynamic dynamic = dynamicService.queryByPK(id);
        dynamicService.delete(dynamic);

        return 1;
    }

    private static Map<String, String> codeMap = new HashMap<String, String>();

    static {
        if (codeMap == null || codeMap.isEmpty()) {
            codeMap = new HashMap<String, String>();
        }
    }

    /**
     * 发送验证码
     */
    @RequestMapping(value = "/sendCode")
    @ResponseBody
    public Integer sendCode(HttpServletResponse response, String num) {
        if (!StringUtils.isNotEmpty(num)) {
            return 0;
        }
        String code = RandomUtil.getCode(6);
        if (vcodeService.sendKX(code, num)) {
            codeMap.put(num, code);
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * 绑定手机
     */
    @RequestMapping(value = "/mobile")
    @ResponseBody
    public Integer mobile(Long userId, String num, String code) {

        String requestCode = codeMap.get(num);
        if (StringUtils.isBlank(code)) {
            return 2;
        }
        if (!requestCode.equals(code)) {
            return 3;
        }

        UserInfo userInfo = userService.queryByPK(userId);
        userInfo.setMobile(num);
        userService.update(userInfo);
        return 1;
    }
}
