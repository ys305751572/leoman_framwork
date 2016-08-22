package com.leoman.work.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.work.dao.WorkComicDao;
import com.leoman.work.dao.WorkVideoDao;
import com.leoman.work.entity.WorkComic;
import com.leoman.work.entity.WorkVideo;
import com.leoman.work.service.WorkComicService;
import com.leoman.work.service.WorkVideoService;
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
public class WorkComicServiceImpl extends GenericManagerImpl<WorkComic, WorkComicDao> implements WorkComicService {

    @Autowired
    private WorkComicDao workComicDao;

    @Override
    public List<WorkComic> findByWorkId(Long workId) {
        return workComicDao.findByWorkId(workId);
    }

    @Override
    public Page<WorkComic> page(final Long workId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<WorkComic> page = workComicDao.findAll(new Specification<WorkComic>() {
            @Override
            public Predicate toPredicate(Root<WorkComic> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
