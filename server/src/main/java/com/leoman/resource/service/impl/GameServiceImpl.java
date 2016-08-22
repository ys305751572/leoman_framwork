package com.leoman.resource.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.resource.service.GameService;
import com.leoman.resource.service.MusicService;
import com.leoman.resources.dao.GameResourcesDao;
import com.leoman.resources.dao.MusicResourceDao;
import com.leoman.resources.entity.GameResource;
import com.leoman.resources.entity.MusicResource;
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
 * Created by Administrator on 2016/6/28 0028.
 */
@Service
public class GameServiceImpl extends GenericManagerImpl<GameResource, GameResourcesDao> implements GameService {

    @Autowired
    private GameResourcesDao gameResourcesDao;

    @Override
    public Page<GameResource> page(Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<GameResource> page = gameResourcesDao.findAll(new Specification<GameResource>() {
            @Override
            public Predicate toPredicate(Root<GameResource> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
