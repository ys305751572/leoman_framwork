package com.leoman.resource.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.resource.service.MusicService;
import com.leoman.resource.service.StillService;
import com.leoman.resources.dao.MusicResourceDao;
import com.leoman.resources.dao.StillResourceDao;
import com.leoman.resources.entity.MusicResource;
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
 * Created by Administrator on 2016/6/28 0028.
 */
@Service
public class MusicServiceImpl extends GenericManagerImpl<MusicResource, MusicResourceDao> implements MusicService {

    @Autowired
    private MusicResourceDao musicResourceDao;

    @Override
    public Page<MusicResource> page(Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<MusicResource> page = musicResourceDao.findAll(new Specification<MusicResource>() {
            @Override
            public Predicate toPredicate(Root<MusicResource> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
