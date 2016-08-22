package com.leoman.reourceapi.service;

import com.leoman.exception.ParamsErrorException;
import com.leoman.exception.PostNotFindException;
import com.leoman.exception.ResourceNotFindException;
import com.leoman.resources.entity.GameResource;
import com.leoman.resources.entity.MusicResource;
import com.leoman.resources.entity.StillResource;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface ResourceApiService {

    // 剧照列表
    public Page<StillResource> iPageStillList(Integer pageNum, Integer pageSize);

    // 音乐列表
    public Page<MusicResource> iPageMusicList(Integer pageNum, Integer pageSize);

    // 游戏列表
    public Page<GameResource> iPageGameList(Integer pageNum, Integer pageSize);

    // 下载资源（播放音乐）
    public Boolean downResource(Long id, Integer type) throws ParamsErrorException, ResourceNotFindException;
}
