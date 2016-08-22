package com.leoman.feedback.service.impl;

import com.leoman.common.controller.common.CommonController;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.feedback.dao.FeedbackDao;
import com.leoman.feedback.entity.Feedback;
import com.leoman.feedback.service.FeedBackService;
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
 * Created by Administrator on 2016/7/1 0001.
 */
@Service
public class FeedBackServiceImpl extends GenericManagerImpl<Feedback, FeedbackDao> implements FeedBackService {

    @Autowired
    private FeedbackDao feedbackDao;

    @Override
    public Page<Feedback> page(Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Feedback> page = feedbackDao.findAll(new Specification<Feedback>() {
            @Override
            public Predicate toPredicate(Root<Feedback> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
    public List<Feedback> findListNew() {
        return feedbackDao.findListNew(CommonController.getOldDate());
    }
}
