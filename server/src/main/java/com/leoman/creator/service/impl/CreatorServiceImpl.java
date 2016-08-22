package com.leoman.creator.service.impl;

import com.leoman.city.dao.CityDao;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.creator.dao.CreatorDao;
import com.leoman.creator.dao.CreatorImageDao;
import com.leoman.creator.entity.Creator;
import com.leoman.creator.entity.CreatorImage;
import com.leoman.creator.service.CreatorService;
import com.leoman.creatordynamic.dao.DynamicDao;
import com.leoman.creatordynamic.entity.Dynamic;
import com.leoman.entity.FileBo;
import com.leoman.province.dao.ProvinceDao;
import com.leoman.user.dao.UserInfoDao;
import com.leoman.user.entity.UserInfo;
import com.leoman.utils.DateUtils;
import com.leoman.utils.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
@Service
public class CreatorServiceImpl extends GenericManagerImpl<Creator, CreatorDao> implements CreatorService {

    @Autowired
    private CreatorDao creatorDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private DynamicDao dynamicDao;

    @Autowired
    private ProvinceDao provinceDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private CreatorImageDao creatorImageDao;

    @Override
    public List<Creator> iFindList() {
        return creatorDao.findAll();
    }

    @Override
    @Transactional
    public Integer saveAll(Long id,
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
                           MultipartRequest multipartRequest) throws IOException, ParseException {
        String[] addImageIds1 = tempAddImageIds1.split(",");
        String[] addImageIds2 = tempAddImageIds2.split(",");
        String[] delImageIds = tempDelImageIds.split(",");

        UserInfo userInfo = null;
        Creator creator = null;
        List<UserInfo> userInfoList = userInfoDao.findAll();
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
            userInfo = userInfoDao.findOne(id);
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

        creator.setProvince(provinceDao.findOne(provinceId));
        creator.setCity(cityDao.findOne(cityId));
        creator.setDescription(description);
        creator.setWeibo(weibo);
        creator.setExperience(experience);

        MultipartFile multipartFile2 = multipartRequest.getFile("imageFile2");
        if (null != multipartFile2) {
            FileBo fileBo = null;
            try {
                fileBo = FileUtil.save(multipartFile2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            creator.setCoverUrl(fileBo.getPath());
        }
        MultipartFile multipartFile1 = multipartRequest.getFile("url");
        if (null != multipartFile1) {
            FileBo fileBo = FileUtil.save(multipartFile1);
            creator.setAudioUrl(fileBo.getPath());
        }
        creatorDao.save(creator);

        userInfo.setNickname(nickname);
        //userInfo.setMobile(mobile);
        userInfo.setPassword(password);
        userInfo.setGender(gender);

        MultipartFile multipartFile = multipartRequest.getFile("imageFile");
        if (null != multipartFile) {
            FileBo fileBo = FileUtil.save(multipartFile);
            userInfo.setAvater(fileBo.getPath());
        }
        userInfo.setCreator(creator);
        userInfo.setIsCreator(1);
        userInfoDao.save(userInfo);

        //修改动态保存
        List<Dynamic> dynamicList = dynamicDao.findByCreatorId(creator.getId());
        if (dynamicList.size() > 0) {
            for (int i = 0; i < times.length; i++) {
                if (StringUtils.isNotEmpty(times[i])) {
                    dynamicList.get(i).setTime(DateUtils.stringToLong(times[i], "yyyy-MM-dd hh:mm:ss"));
                    dynamicList.get(i).setDate(times[i].substring(0, 11));
                    dynamicList.get(i).setCreator(creator);
                    dynamicList.get(i).setStatus(things[i]);

                    dynamicDao.save(dynamicList.get(i));
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
                if (thing[i] != null) {
                    dynamic.setStatus(thing[i]);
                }
                dynamicDao.save(dynamic);
            }
        }

        // 保存剧照图片集合
        CreatorImage image = null;
        for (String imageId : addImageIds1) {
            if (null != imageId && !imageId.equals("")) {
                image = creatorImageDao.findOne(Long.parseLong(imageId));
                //image.setImage();
                image.setType(0);
                image.setCreator(creator);

                creatorImageDao.save(image);
            }
        }

        // 保存生活照图片集合
        CreatorImage image2 = null;
        for (String imageId : addImageIds2) {
            if (null != imageId && !imageId.equals("")) {
                image2 = creatorImageDao.findOne(Long.parseLong(imageId));
                //image2.setImage();
                image2.setType(1);
                image2.setCreator(creator);

                creatorImageDao.save(image2);
            }
        }

        // 删除图片集合
        CreatorImage image3 = null;
        for (String imageId : delImageIds) {
            if (null != imageId && !imageId.equals("")) {
                image3 = creatorImageDao.findOne(Long.parseLong(imageId));
                creatorImageDao.delete(image3);
            }
        }
        return 1;
    }
}
