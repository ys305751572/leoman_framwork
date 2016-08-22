package com.leoman.creator.service;

import com.leoman.common.service.GenericManager;
import com.leoman.creator.entity.CreatorImage;

import java.util.List;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
public interface CreatorImageService extends GenericManager<CreatorImage>{

    public List<CreatorImage> findByCreatorId(Long creatorId);
}
