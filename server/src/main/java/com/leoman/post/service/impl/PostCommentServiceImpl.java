package com.leoman.post.service.impl;

import com.leoman.comment.entity.Comment;
import com.leoman.common.controller.common.CommonController;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.post.dao.PostCommentDao;
import com.leoman.post.entity.PostComment;
import com.leoman.post.service.PostCommentService;
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
 * Created by Administrator on 2016/7/20 0020.
 */
@Service
public class PostCommentServiceImpl extends GenericManagerImpl<PostComment, PostCommentDao> implements PostCommentService {

    @Autowired
    private PostCommentDao postCommentDao;

    @Override
    public Page<PostComment> pageList(final String content, final String mobile, final Integer type, final Integer status, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<PostComment> page = postCommentDao.findAll(new Specification<PostComment>() {
            @Override
            public Predicate toPredicate(Root<PostComment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (StringUtils.isNotEmpty(content)) {
                    Predicate pre = cb.like(root.get("content").as(String.class), "%" + content + "%");
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

                if (status != null) {
                    Predicate pre = cb.equal(root.get("status").as(Integer.class), status);
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
    public Page<PostComment> page(final String name, final String mobile, final Integer type, final Integer status, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<PostComment> page = postCommentDao.findAll(new Specification<PostComment>() {
            @Override
            public Predicate toPredicate(Root<PostComment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (StringUtils.isNotEmpty(name)) {
                    Predicate pre = cb.like(root.get("postBase").get("name").as(String.class), "%" + name + "%");
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
                else {
                    Predicate pre = cb.ge(root.get("type").as(Integer.class),0);
                    predicateList.add(pre);

                    Predicate pre2 = cb.le(root.get("type").as(Integer.class),2);
                    predicateList.add(pre2);

                }

                if (status != null) {
                    Predicate pre = cb.equal(root.get("status").as(Integer.class), status);
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
    public List<PostComment> findListNew(Integer type) {
        return postCommentDao.findListNew(CommonController.getOldDate(), type);
    }
}
