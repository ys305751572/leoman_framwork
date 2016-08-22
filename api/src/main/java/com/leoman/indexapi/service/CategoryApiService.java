package com.leoman.indexapi.service;

import com.leoman.category.entity.Category;
import com.leoman.exception.ParamsErrorException;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface CategoryApiService {

    // 主页查询作品集列表
    public Page<Category> iFindList(Integer pageNum, Integer pageSize);
}
