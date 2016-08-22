package com.leoman.work.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.work.dao.WorkVideoDao;
import com.leoman.work.entity.Work;
import com.leoman.work.entity.WorkVideo;
import com.leoman.work.service.WorkVideoService;
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
 * Created by Administrator on 2016/6/16 0016.
 */
@Service
public class WorkVideoServiceImpl extends GenericManagerImpl<WorkVideo, WorkVideoDao> implements WorkVideoService {

    @Autowired
    private WorkVideoDao workVideoDao;

    @Override
    public List<WorkVideo> findByWorkId(Long workId) {
        return workVideoDao.findByWorkId(workId);
    }

    @Override
    public Page<WorkVideo> page(final Long workId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<WorkVideo> page = workVideoDao.findAll(new Specification<WorkVideo>() {
            @Override
            public Predicate toPredicate(Root<WorkVideo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (workId != null) {
                    Predicate pre = cb.equal(root.get("work").get("id").as(Long.class), workId);
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
}
