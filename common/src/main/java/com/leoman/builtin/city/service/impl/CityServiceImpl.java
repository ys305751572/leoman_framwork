package com.leoman.builtin.city.service.impl;

import com.leoman.builtin.city.dao.CityDao;
import com.leoman.builtin.city.entity.City;
import com.leoman.builtin.city.service.CityService;
import com.leoman.common.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/16 0016.
 */
@Service
public class CityServiceImpl  extends GenericManagerImpl<City, CityDao> implements CityService {

    @Autowired
    private CityDao cityDao;

    @Override
    public List<City> findById(Long provinceId) {
        return cityDao.findById(provinceId);
    }
}
