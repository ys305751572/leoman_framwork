package com.leoman.work.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.creator.entity.CreatorImage;
import com.leoman.work.entity.WorkComicImage;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface WorkComicImageDao extends IBaseJpaRepository<WorkComicImage> {

    @Query("select a from WorkComicImage a where a.workComic.id = ?1 ")
    public List<WorkComicImage> findByComicId(Long comicId);
}
