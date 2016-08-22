package com.leoman.comment.service.impl;

import com.leoman.barrage.entity.Barrage;
import com.leoman.comment.dao.CommentDao;
import com.leoman.comment.entity.Comment;
import com.leoman.comment.service.CommentService;
import com.leoman.common.controller.common.CommonController;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.post.entity.PostBase;
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
 * Created by Administrator on 2016/6/22 0022.
 */
@Service
public class CommentServiceImpl extends GenericManagerImpl<Comment, CommentDao> implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public Page<Comment> pageList(final String name, final String mobile, final Integer type, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Comment> page = commentDao.findAll(new Specification<Comment>() {
            @Override
            public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (StringUtils.isNotEmpty(name)) {
                    Predicate pre = cb.like(root.get("fromUser").get("mobile").as(String.class), "%" + name + "%");
                    predicateList.add(pre);
                }

                if (StringUtils.isNotEmpty(mobile)) {
                    Predicate pre = cb.like(root.get("fromUser").get("mobile").as(String.class), "%" + mobile + "%");
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
    public List<Comment> findListNew(Integer type) {
        return commentDao.findListNew(CommonController.getOldDate(), type);
    }
}
