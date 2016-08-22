package com.leoman.creator.service;

import com.leoman.common.service.GenericManager;
import com.leoman.creator.entity.Creator;
import com.leoman.user.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
public interface CreatorService extends GenericManager<Creator> {

    public List<Creator> iFindList();

    public Integer saveAll(Long id,
                           String nickname,
                           String password,
                           Integer gender,
                           Long provinceId,
                           Long cityId,
                           String description,
                           String weibo,
                           String experience,
                           String[] time,
                           Integer[] thing,
                           String tempAddImageIds1,
                           String tempAddImageIds2,
                           String tempDelImageIds,
                           String[] times,
                           Integer[] things,
                           MultipartRequest multipartRequest) throws IOException, ParseException;
}
