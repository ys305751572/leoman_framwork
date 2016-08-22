package com.leoman.work.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.work.dao.WorkNovelDao;
import com.leoman.work.dao.WorkVideoDao;
import com.leoman.work.entity.WorkNovel;
import com.leoman.work.entity.WorkVideo;
import com.leoman.work.service.WorkNovelService;
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
 * Created by Administrator on 2016/6/21 0021.
 */
@Service
public class WorkNovelServiceImpl extends GenericManagerImpl<WorkNovel, WorkNovelDao> implements WorkNovelService {

    @Autowired
    private WorkNovelDao workNovelDao;

    @Override
    public List<WorkNovel> findByWorkId(Long workId) {
        return workNovelDao.findByWorkId(workId);
    }

    @Override
    public Page<WorkNovel> page(final Long workId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<WorkNovel> page = workNovelDao.findAll(new Specification<WorkNovel>() {
            @Override
            public Predicate toPredicate(Root<WorkNovel> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
