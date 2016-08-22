package com.leoman.welfare.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.welfare.dao.WelfareDao;
import com.leoman.welfare.entity.Welfare;
import com.leoman.welfare.service.WelfareService;
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
 * Created by Administrator on 2016/6/29 0029.
 */
@Service
public class WelfareServiceImpl extends GenericManagerImpl<Welfare, WelfareDao> implements WelfareService {

    @Autowired
    private WelfareDao welfareDao;

    @Override
    public Page<Welfare> page(Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Welfare> page = welfareDao.findAll(new Specification<Welfare>() {
            @Override
            public Predicate toPredicate(Root<Welfare> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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

    @Override
    public List<Welfare> findByType(Integer type) {
        return welfareDao.findByType(type);
    }

}
