package com.leoman.systemsetting.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.systemsetting.dao.PrizeDao;
import com.leoman.systemsetting.entity.Prize;
import com.leoman.systemsetting.service.PrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2016/6/30 0030.
 */
@Service
public class PrizeServiceImpl extends GenericManagerImpl<Prize, PrizeDao> implements PrizeService {

    @Autowired
    private PrizeDao prizeDao;

    @Override
    public Prize findByCoin(Integer coin) {
        return prizeDao.findByCoin(coin);
    }

    @Override
    @Transactional
    public void saveAll(Double name1, Double name2, Double name3, Double name4, Double name5, Double name6, Double name7, Double name8) {
        Prize prize1 = prizeDao.findByCoin(0);
        prize1.setPro(name1);

        Prize prize2 = prizeDao.findByCoin(10);
        prize2.setPro(name2);

        Prize prize3 = prizeDao.findByCoin(19);
        prize3.setPro(name3);

        Prize prize4 = prizeDao.findByCoin(29);
        prize4.setPro(name4);

        Prize prize5 = prizeDao.findByCoin(99);
        prize5.setPro(name5);

        Prize prize6 = prizeDao.findByCoin(199);
        prize6.setPro(name6);

        Prize prize7 = prizeDao.findByCoin(499);
        prize7.setPro(name7);

        Prize prize8 = prizeDao.findByCoin(999);
        prize8.setPro(name8);

        prizeDao.save(prize1);
        prizeDao.save(prize2);
        prizeDao.save(prize3);
        prizeDao.save(prize4);
        prizeDao.save(prize5);
        prizeDao.save(prize6);
        prizeDao.save(prize7);
        prizeDao.save(prize8);
    }
}
