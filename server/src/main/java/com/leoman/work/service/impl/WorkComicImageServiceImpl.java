package com.leoman.work.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.work.dao.WorkComicDao;
import com.leoman.work.dao.WorkComicImageDao;
import com.leoman.work.entity.WorkComic;
import com.leoman.work.entity.WorkComicImage;
import com.leoman.work.service.WorkComicImageService;
import com.leoman.work.service.WorkComicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
@Service
public class WorkComicImageServiceImpl extends GenericManagerImpl<WorkComicImage, WorkComicImageDao> implements WorkComicImageService {

    @Autowired
    private WorkComicImageDao workComicImageDao;

    @Override
    public List<WorkComicImage> findByComicId(Long comicId) {
        return workComicImageDao.findByComicId(comicId);
    }
}
