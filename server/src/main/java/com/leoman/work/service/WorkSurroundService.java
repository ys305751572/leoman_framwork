package com.leoman.work.service;

import com.leoman.common.service.GenericManager;
import com.leoman.work.entity.Work;
import com.leoman.work.entity.WorkSurround;
import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/6/17 0017.
 */
public interface WorkSurroundService extends GenericManager<WorkSurround>{

    public List<WorkSurround> findByWorkId(Long workId);

    public void saveAll(Long id,
                        String name,
                        Integer seriesCount,
                        Long category,
                        String description,
                        String[] creator,
                        String[] name2,
                        String[] description2,
                        String[] linkUrl,
                        String[] names,
                        String[] descriptions,
                        String[] linkUrls,
                        MultipartRequest multipartRequest) throws IOException;

    // void addAll(String[] name2, String[] description2, String[] linkUrl);

}
