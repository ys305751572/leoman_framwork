package com.leoman.indexapi.service.Impl;

import com.leoman.creatordynamic.dao.CreatorDynamicDao;
import com.leoman.creatordynamic.entity.CreatorDynamic;
import com.leoman.creatordynamic.entity.vo.CreatorDynamicVo;
import com.leoman.creatordynamic.entity.vo.DynamicVo;
import com.leoman.entity.Configue;
import com.leoman.exception.CreatorNotFindException;
import com.leoman.indexapi.service.CreatorDynamicApiService;
import com.leoman.user.entity.UserInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
@Service
public class CreatorDynamicApiServiceImplApi implements CreatorDynamicApiService {

    @Autowired
    private CreatorDynamicDao creatorDynamicDao;

    @Override
    public List<CreatorDynamicVo> iFindList(Long creatorId) throws CreatorNotFindException {
        return getCreatorDynamicVoList(creatorId, "");
    }

    @Override
    public List<DynamicVo> iFindList2(Long creatorId) throws CreatorNotFindException {
        List<DynamicVo> dynamicVoList = new ArrayList<DynamicVo>();
        DynamicVo dynamicVo = null;

        // 先获取所有的动态日期
        List<String> firstList = null;

        if (null == creatorId) {
            firstList = creatorDynamicDao.findList();
        } else {
            firstList = creatorDynamicDao.findListByCreatorId(creatorId);
        }

        for (String date : firstList) {
            dynamicVo = new DynamicVo();
            dynamicVo.setDate(date);
            dynamicVo.setDynamicList(getCreatorDynamicVoList(creatorId, date));

            dynamicVoList.add(dynamicVo);
        }

        return dynamicVoList;
    }

    private List<CreatorDynamicVo> getCreatorDynamicVoList(Long creatorId, String date) {
        List<CreatorDynamicVo> dynamicVoList = new ArrayList<CreatorDynamicVo>();
        CreatorDynamicVo creatorDynamicVo = null;
        List<CreatorDynamic> list = null;

        if (null == creatorId) {
            if (StringUtils.isNotEmpty(date)) {
                list = creatorDynamicDao.findListByDate(date);
            } else {
                list = creatorDynamicDao.findListByDatePlus();
            }
        } else {
            if (StringUtils.isNotEmpty(date)) {
                list = creatorDynamicDao.findListByCreatorIdAndDate(creatorId, date);
            } else {
                list = creatorDynamicDao.findListByCreatorIdPlus(creatorId);
            }
        }
        UserInfo userInfo = null;

        for (CreatorDynamic creatorDynamic : list) {
            userInfo = creatorDynamic.getUserInfo();
            creatorDynamicVo = new CreatorDynamicVo();
            creatorDynamicVo.setId(creatorDynamic.getId());
            creatorDynamicVo.setCreatorId(userInfo.getCreator().getId());
            creatorDynamicVo.setUserId(userInfo.getId());
            creatorDynamicVo.setName(userInfo.getNickname());
            creatorDynamicVo.setAvater(Configue.getUploadUrl() + userInfo.getAvater());
            creatorDynamicVo.setStatus(creatorDynamic.getStatus());

            dynamicVoList.add(creatorDynamicVo);
        }

        return dynamicVoList;
    }
}
