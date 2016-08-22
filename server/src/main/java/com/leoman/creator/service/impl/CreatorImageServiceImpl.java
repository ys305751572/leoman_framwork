package com.leoman.creator.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.creator.dao.CreatorDao;
import com.leoman.creator.dao.CreatorImageDao;
import com.leoman.creator.entity.Creator;
import com.leoman.creator.entity.CreatorImage;
import com.leoman.creator.service.CreatorImageService;
import com.leoman.creator.service.CreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
@Service
public class CreatorImageServiceImpl extends GenericManagerImpl<CreatorImage, CreatorImageDao> implements CreatorImageService {

    @Autowired
    private CreatorImageDao creatorImageDao;

    @Override
    public List<CreatorImage> findByCreatorId(Long creatorId) {
        return creatorImageDao.findByCreatorId(creatorId);
    }
}
