package com.leoman.indexapi.service;

import com.leoman.creatordynamic.entity.vo.CreatorDynamicVo;
import com.leoman.creatordynamic.entity.vo.DynamicVo;
import com.leoman.exception.CreatorNotFindException;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface CreatorDynamicApiService {

    // 根据主创ID和时间查询主创动态列表
    public List<CreatorDynamicVo> iFindList(Long creatorId) throws CreatorNotFindException;

    // 根据主创ID和时间查询主创动态列表
    public List<DynamicVo> iFindList2(Long creatorId) throws CreatorNotFindException;
}
