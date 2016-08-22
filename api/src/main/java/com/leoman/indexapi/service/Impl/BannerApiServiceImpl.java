package com.leoman.indexapi.service.Impl;

import com.leoman.banner.dao.BannerDao;
import com.leoman.banner.entity.Banner;
import com.leoman.entity.Configue;
import com.leoman.indexapi.service.BannerApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
@Service
public class BannerApiServiceImpl implements BannerApiService {

    @Autowired
    private BannerDao bannerDao;

    @Override
    public List<Banner> iFindList() {
        List<Banner> list = bannerDao.findAll();

        for (Banner banner : list) {
            banner.setCover(Configue.getUploadUrl() + banner.getCover());
        }

        return list;
    }
}
