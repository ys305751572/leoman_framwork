package com.leoman.post.service;

import com.leoman.common.service.GenericManager;
import com.leoman.post.entity.PostBase;
import com.leoman.user.entity.UserInfo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/6/27 0027.
 */
public interface PostBaseService extends GenericManager<PostBase>{

    //查询用户帖子列表
    public Page<PostBase> page(String content, String mobile, Long category, Integer status, Integer type, Integer pageNum, Integer pageSize);

    // 获取帖子基本信息
    public PostBase getPostInfo(Long postId, Integer type);

    //查询官方帖子列表
    public Page<PostBase> findPage(String name, Long category, Integer status, Integer type, Integer pageNum, Integer pageSize);

    public List<PostBase> findByCategoryId(Long categoryId, Integer type);

    public List<PostBase> findByCategoryId(Long categoryId);

    // 查询新增用户帖子
    public List<PostBase> findListNew(Integer type);

    //查询类型查询帖子列表
    public List<PostBase> findByType(Integer type);

    //查询加精帖子列表
    public List<PostBase> findByEssence(Integer essence);

    //查询锁帖列表
    public List<PostBase> findByStatus(Integer status, Integer type);
}
