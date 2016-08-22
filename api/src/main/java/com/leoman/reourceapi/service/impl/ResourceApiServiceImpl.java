package com.leoman.reourceapi.service.impl;

import com.leoman.entity.Configue;
import com.leoman.entity.Constant;
import com.leoman.exception.ParamsErrorException;
import com.leoman.exception.ResourceNotFindException;
import com.leoman.post.entity.vo.PostImageVo;
import com.leoman.reourceapi.service.ResourceApiService;
import com.leoman.resources.dao.GameResourcesDao;
import com.leoman.resources.dao.MusicResourceDao;
import com.leoman.resources.dao.StillResourceDao;
import com.leoman.resources.entity.GameResource;
import com.leoman.resources.entity.MusicResource;
import com.leoman.resources.entity.StillResource;
import com.leoman.resources.entity.vo.MusicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
@Service
public class ResourceApiServiceImpl implements ResourceApiService {

    @Autowired
    private StillResourceDao stillResourceDao;

    @Autowired
    private MusicResourceDao musicResourceDao;

    @Autowired
    private GameResourcesDao gameResourcesDao;

    @Override
    public Page<StillResource> iPageStillList(Integer pageNum, Integer pageSize) {
        int[] info = changePageParams(pageNum, pageSize);
        Page<StillResource> page = stillResourceDao.iPageListGroupByCategoryId(new PageRequest(info[0] - 1, info[1]));

        for (StillResource stillResource : page.getContent()) {
            stillResource.setName(stillResource.getCategory().getName());
            stillResource.setImageList(getStillVo(stillResource.getCategory().getId()));
            stillResource.setImageUrl(stillResource.getImageList().get(0).getUrl());
        }

        return page;
    }

    @Override
    public Page<MusicResource> iPageMusicList(Integer pageNum, Integer pageSize) {
        int[] info = changePageParams(pageNum, pageSize);
        Page<MusicResource> page = musicResourceDao.iPageListGroupByCategoryId(new PageRequest(info[0] - 1, info[1]));

        for (MusicResource musicResource : page.getContent()) {
            musicResource.setName(musicResource.getCategory().getName());
            musicResource.setCover(Configue.getUploadUrl() + musicResource.getCategory().getUrl());
            musicResource.setMusicList(getMusicVo(musicResource.getCategory().getId()));
            musicResource.setSize(musicResource.getMusicList().size());
        }

        return page;
    }

    @Override
    public Page<GameResource> iPageGameList(Integer pageNum, Integer pageSize) {
        int[] info = changePageParams(pageNum, pageSize);
        Page<GameResource> page = gameResourcesDao.findAll(new PageRequest(info[0] - 1, info[1]));

        for (GameResource gameResource : page.getContent()) {
            gameResource.setUrl(Configue.getUploadUrl() + gameResource.getUrl());
        }

        return page;
    }

    @Override
    public Boolean downResource(Long id, Integer type) throws ParamsErrorException, ResourceNotFindException {
        if (null == id || null == type) {
            throw new ParamsErrorException();
        }

        Boolean flag = false;

        switch (type) {
            case 1:
                // 剧照
                StillResource stillResource = stillResourceDao.findOne(id);
                if (null != stillResource) {
                    stillResource.setNum(stillResource.getNum() + 1);
                    flag = true;
                }
                break;
            case 2:
                // 音乐
                MusicResource musicResource = musicResourceDao.findOne(id);
                if (null != musicResource) {
                    musicResource.setNum(musicResource.getNum() + 1);
                    flag = true;
                }
                break;
            case 3:
                // 游戏
                GameResource gameResource = gameResourcesDao.findOne(id);
                if (null != gameResource) {
                    gameResource.setDownCount(gameResource.getDownCount() + 1);
                    flag = true;
                }
                break;
        }

        return flag;
    }

    /**
     * 根据类别id获取音乐列表
     *
     * @param categoryId
     * @return
     */
    private List<MusicVo> getMusicVo(Long categoryId) {
        List<MusicVo> list = new ArrayList<MusicVo>();
        MusicVo musicVo = null;
        List<MusicResource> musicResourceList = musicResourceDao.iFindListByCategoryId(categoryId);

        for (MusicResource musicResource : musicResourceList) {
            musicVo = new MusicVo();
            musicVo.setId(musicResource.getId());
            musicVo.setName(musicResource.getName());
            musicVo.setSingerName(musicResource.getSinger());
            musicVo.setPlayNum(musicResource.getNum());
            musicVo.setUrl(Configue.getUploadUrl() + musicResource.getImageUrl());

            list.add(musicVo);
        }

        return list;
    }

    /**
     * 根据类别id获取剧照列表
     *
     * @param categoryId
     * @return
     */
    private List<PostImageVo> getStillVo(Long categoryId) {
        List<PostImageVo> list = new ArrayList<PostImageVo>();

        PostImageVo image = null;
        List<StillResource> stillResourceList = stillResourceDao.iFindListByCategoryId(categoryId);

        for (StillResource still : stillResourceList) {
            image = new PostImageVo();
            image.setId(still.getId());
            image.setUrl(Configue.getUploadUrl() + still.getImageUrl());

            list.add(image);
        }

        return list;
    }

    public static int[] changePageParams(Integer pageNum, Integer pageSize) {
        int[] info = new int[2];

        if (null == pageNum) {
            pageNum = 1;
        }
        if (null == pageSize) {
            pageSize = Constant.PAGE_DEF_SIZE;
        }

        info[0] = pageNum;
        info[1] = pageSize;

        return info;
    }
}
