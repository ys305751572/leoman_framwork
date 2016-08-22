package com.leoman.recordcount.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.recordcount.dao.RecordCountDao;
import com.leoman.recordcount.entity.RecordCount;
import com.leoman.recordcount.service.RecordCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/7/22 0022.
 */
@Service
public class RecordCountServiceImpl extends GenericManagerImpl<RecordCount, RecordCountDao> implements RecordCountService {

    @Autowired
    private RecordCountDao recordCountDao;

    @Override
    public void addCount(Integer visitCount, Integer onlineCount) {
        RecordCount recordCount = recordCountDao.findOne(1l);
        recordCount.setVisitCount(recordCount.getVisitCount() + visitCount);
        recordCount.setOnlineCount(recordCount.getOnlineCount() + onlineCount);

        recordCountDao.save(recordCount);
    }

    @Override
    public void clearCount() {
        RecordCount recordCount = recordCountDao.findOne(1l);
        recordCount.setVisitCount(0);
        recordCount.setOnlineCount(0);

        recordCountDao.save(recordCount);
    }
}
