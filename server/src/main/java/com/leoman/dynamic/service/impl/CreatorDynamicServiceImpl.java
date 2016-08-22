package com.leoman.dynamic.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.creatordynamic.dao.CreatorDynamicDao;
import com.leoman.creatordynamic.entity.CreatorDynamic;
import com.leoman.dynamic.service.CreatorDynamicService;
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
 * Created by Administrator on 2016/6/30 0030.
 */
@Service
public class CreatorDynamicServiceImpl extends GenericManagerImpl<CreatorDynamic, CreatorDynamicDao> implements CreatorDynamicService {

    @Autowired
    private CreatorDynamicDao creatorDynamicDao;

    @Override
    public Page<CreatorDynamic> page(Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<CreatorDynamic> page = creatorDynamicDao.findAll(new Specification<CreatorDynamic>() {
            @Override
            public Predicate toPredicate(Root<CreatorDynamic> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
