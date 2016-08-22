package com.leoman.work.service;

import com.leoman.common.service.GenericManager;
import com.leoman.work.entity.WorkComic;
import com.leoman.work.entity.WorkNovel;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
public interface WorkComicService extends GenericManager<WorkComic>{

    public List<WorkComic> findByWorkId(Long workId);

    public Page<WorkComic> page(Long workId, Integer pageNum, Integer pageSize);
}
