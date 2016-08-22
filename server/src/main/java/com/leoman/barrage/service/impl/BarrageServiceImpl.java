package com.leoman.barrage.service.impl;

import com.leoman.barrage.dao.BarrageDao;
import com.leoman.barrage.entity.Barrage;
import com.leoman.barrage.service.BarrageService;
import com.leoman.common.controller.common.CommonController;
import com.leoman.common.service.impl.GenericManagerImpl;
import org.apache.commons.lang.StringUtils;
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
 * Created by Administrator on 2016/6/20 0020.
 */
@Service
public class BarrageServiceImpl extends GenericManagerImpl<Barrage, BarrageDao> implements BarrageService {

    @Autowired
    private BarrageDao barrageDao;

    @Override
    public Page<Barrage> page(final Long workId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Barrage> page = barrageDao.findAll(new Specification<Barrage>() {
            @Override
            public Predicate toPredicate(Root<Barrage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (workId != null) {
                    Predicate pre = cb.equal(root.get("workId").as(Long.class), workId);
                    predicateList.add(pre);
                }

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
    public Page<Barrage> pageList(final String name, final String mobile, final Integer type, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Barrage> page = barrageDao.findAll(new Specification<Barrage>() {
            @Override
            public Predicate toPredicate(Root<Barrage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (StringUtils.isNotEmpty(name)) {
                    Predicate pre = cb.like(root.get("userLogin").get("username").as(String.class), "%" + name + "%");
                    predicateList.add(pre);
                }

                if (StringUtils.isNotEmpty(mobile)) {
                    Predicate pre = cb.like(root.get("userLogin").get("username").as(String.class), "%" + mobile + "%");
                    predicateList.add(pre);
                }

                if (type != null) {
                    Predicate pre = cb.equal(root.get("type").as(Integer.class), type);
                    predicateList.add(pre);
                }

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
    public List<Barrage> findListNew(Integer type) {
        return barrageDao.findListNew(CommonController.getOldDate(), type);
    }
}
