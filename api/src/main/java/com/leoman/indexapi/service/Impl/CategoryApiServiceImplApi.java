package com.leoman.indexapi.service.Impl;

import com.leoman.category.dao.CategoryDao;
import com.leoman.category.entity.Category;
import com.leoman.entity.Configue;
import com.leoman.entity.Constant;
import com.leoman.exception.ParamsErrorException;
import com.leoman.indexapi.service.CategoryApiService;
import com.leoman.reourceapi.service.impl.ResourceApiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
@Service
public class CategoryApiServiceImplApi implements CategoryApiService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public Page<Category> iFindList(Integer pageNum, Integer pageSize) {
        int[] info = ResourceApiServiceImpl.changePageParams(pageNum, pageSize);
        Page<Category> page = categoryDao.iPage(new PageRequest(info[0] - 1, info[1]));

        for (Category category : page.getContent()) {
            category.setUrl(Configue.getUploadUrl() + category.getUrl());
        }

        return page;
    }
}
