package com.leoman.work.service;

import com.leoman.common.service.GenericManager;
import com.leoman.user.entity.UserInfo;
import com.leoman.work.entity.Work;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Administrator on 2016/6/16 0016.
 */
public interface WorkService extends GenericManager<Work>{

    public Page<Work> page(String name, Long category, Integer status,  Integer type, Integer pageNum, Integer pageSize);

    public List<Work> iFindList(Long category);

    public List<Work> findByType(Integer type);

    public Work findBySoHuId(Long sohuId);

    public Page<Work> page(String name, Long category, Integer status, Long souhuId, Integer pageNum, Integer pageSize);

    //public void update(HttpServletRequest request, Long workId, Long workVideoId, Integer num, Integer status) throws UnsupportedEncodingException;
}
