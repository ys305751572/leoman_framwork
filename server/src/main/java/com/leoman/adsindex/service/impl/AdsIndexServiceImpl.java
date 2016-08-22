package com.leoman.adsindex.service.impl;

import com.leoman.adsindex.dao.AdsIndexDao;
import com.leoman.adsindex.entity.AdsIndex;
import com.leoman.adsindex.service.AdsIndexService;
import com.leoman.banner.dao.BannerDao;
import com.leoman.banner.entity.Banner;
import com.leoman.common.service.impl.GenericManagerImpl;
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
 * Created by Administrator on 2016/7/6 0006.
 */
@Service
public class AdsIndexServiceImpl extends GenericManagerImpl<AdsIndex, AdsIndexDao> implements AdsIndexService {

    @Autowired
    private AdsIndexDao adsIndexDao;

    @Override
    public Page<AdsIndex> page(Integer pageNum, Integer pageSize) {
//        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");
//        Page<AdsIndex> page = adsIndexDao.findAll(new Specification<AdsIndex>() {
//            @Override
//            public Predicate toPredicate(Root<AdsIndex> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                Predicate result = null;
//                List<Predicate> predicateList = new ArrayList<Predicate>();
//
//                if (predicateList.size() > 0) {
//                    result = cb.and(predicateList.toArray(new Predicate[]{}));
//                }
//
//                if (result != null) {
//                    query.where(result);
//                }
//                return query.getGroupRestriction();
//            }
//
//        }, pageRequest);
        return adsIndexDao.findAll(new PageRequest(pageNum - 1,pageSize, Sort.Direction.DESC,"id"));

    }
}
