package com.leoman.indexapi.service.Impl;

import com.leoman.entity.Configue;
import com.leoman.entity.Constant;
import com.leoman.floatwin.dao.FloatWinDao;
import com.leoman.floatwin.entity.FloatWin;
import com.leoman.indexapi.service.FloatWinApiService;
import com.leoman.post.dao.PostBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/6/12.
 */
@Service
public class FloatWinApiServiceImpl implements FloatWinApiService {

    @Autowired
    private FloatWinDao floatWinDao;

    @Autowired
    private PostBaseDao postBaseDao;

    @Override
    public FloatWin getFloatWinInfo() {
        FloatWin floatWin = floatWinDao.findOneInfo();
        if (null != floatWin) {
            floatWin.setWorkId(floatWin.getCategoryId());
            floatWin.setCover(Configue.getUploadUrl() + floatWin.getCover());

            if (floatWin.getPosition() == 0) {
                // 如果为帖子，则需要查询workId
                floatWin.setWorkId(Long.parseLong(postBaseDao.findOne(floatWin.getDetailId()).getType().toString()));
            }
        }
        return floatWin;
    }
}
