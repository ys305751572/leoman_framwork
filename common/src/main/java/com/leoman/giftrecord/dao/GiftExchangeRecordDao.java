package com.leoman.giftrecord.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.giftrecord.entity.GiftExchangeRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface GiftExchangeRecordDao extends IBaseJpaRepository<GiftExchangeRecord> {

    @Query("select a from GiftExchangeRecord a where a.userInfo.id = ?1")
    public Page<GiftExchangeRecord> findListByUserId(Long userId, Pageable pageable);
}
