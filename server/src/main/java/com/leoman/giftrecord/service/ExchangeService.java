package com.leoman.giftrecord.service;

import com.leoman.common.service.GenericManager;
import com.leoman.giftrecord.entity.GiftExchangeRecord;
import com.leoman.resources.entity.StillResource;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/7/5 0005.
 */
public interface ExchangeService extends GenericManager<GiftExchangeRecord>{

    public Page<GiftExchangeRecord> page(Integer pageNum, Integer pageSize);
}
