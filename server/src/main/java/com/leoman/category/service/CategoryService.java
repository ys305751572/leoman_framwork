package com.leoman.category.service;

import com.leoman.category.entity.Category;
import com.leoman.common.service.GenericManager;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
public interface CategoryService extends GenericManager<Category> {

    public List<Category> findByType(Integer type);

    public List<Category> findType(Integer type);

    // 首页查询作品集列表
    public List<Category> iFindList();

    public Page<Category> page(Integer type, Integer pageNum, Integer pageSize);

    public Page<Category> findPage(Integer type, Integer status, Integer pageNum, Integer pageSize);
}
