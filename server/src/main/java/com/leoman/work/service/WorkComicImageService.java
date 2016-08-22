package com.leoman.work.service;

import com.leoman.common.service.GenericManager;
import com.leoman.work.entity.WorkComic;
import com.leoman.work.entity.WorkComicImage;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
public interface WorkComicImageService extends GenericManager<WorkComicImage>{

    public List<WorkComicImage> findByComicId(Long comicId);
}
