package com.leoman.systemsetting.service;

import com.leoman.common.service.GenericManager;
import com.leoman.systemsetting.entity.Prize;

/**
 * Created by Administrator on 2016/6/30 0030.
 */
public interface PrizeService extends GenericManager<Prize>{

    public Prize findByCoin(Integer coin);

    public void saveAll(Double name1,
                        Double name2,
                        Double name3,
                        Double name4,
                        Double name5,
                        Double name6,
                        Double name7,
                        Double name8);
}
