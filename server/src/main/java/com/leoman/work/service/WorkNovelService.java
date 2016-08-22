package com.leoman.work.service;

import com.leoman.common.service.GenericManager;
import com.leoman.work.entity.WorkNovel;
import com.leoman.work.entity.WorkVideo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
public interface WorkNovelService extends GenericManager<WorkNovel>{

    public List<WorkNovel> findByWorkId(Long workId);

    public Page<WorkNovel> page(Long workId, Integer pageNum, Integer pageSize);
}
