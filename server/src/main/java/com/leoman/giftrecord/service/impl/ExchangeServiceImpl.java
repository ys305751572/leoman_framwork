package com.leoman.giftrecord.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.giftrecord.dao.GiftExchangeRecordDao;
import com.leoman.giftrecord.entity.GiftExchangeRecord;
import com.leoman.giftrecord.service.ExchangeService;
import com.leoman.resources.dao.StillResourceDao;
import com.leoman.resources.entity.StillResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/5 0005.
 */
@Service
public class ExchangeServiceImpl extends GenericManagerImpl<GiftExchangeRecord, GiftExchangeRecordDao> implements ExchangeService {

    @Autowired
    private GiftExchangeRecordDao giftExchangeRecordDao;

    @Override
    public Page<GiftExchangeRecord> page(Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<GiftExchangeRecord> page = giftExchangeRecordDao.findAll(new Specification<GiftExchangeRecord>() {
            @Override
            public Predicate toPredicate(Root<GiftExchangeRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (predicateList.size() > 0) {
                    result = cb.and(predicateList.toArray(new Predicate[]{}));
                }

                if (result != null) {
                    query.where(result);
                }
                return query.getGroupRestriction();
            }

        }, pageRequest);

        return page;
    }
}
