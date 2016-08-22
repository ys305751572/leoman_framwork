package com.leoman.post.service.impl;

import com.leoman.common.controller.common.CommonController;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.post.dao.PostBaseDao;
import com.leoman.post.entity.PostBase;
import com.leoman.post.service.PostBaseService;
import com.leoman.user.entity.UserInfo;
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
 * Created by Administrator on 2016/6/27 0027.
 */
@Service
public class PostBaseServiceImpl extends GenericManagerImpl<PostBase, PostBaseDao> implements PostBaseService {

    @Autowired
    private PostBaseDao postBaseDao;

    @Override
    public Page<PostBase> page(final String content, final String mobile, final Long category, final Integer status, final Integer type, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<PostBase> page = postBaseDao.findAll(new Specification<PostBase>() {
            @Override
            public Predicate toPredicate(Root<PostBase> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                /*UserPost userPost = userPostService.queryByPK((Serializable) root.get("postId").as(Long.class));

                if (StringUtils.isNotEmpty(content)) {
                    Predicate pre = cb.like(userPost.getContent(), "%" + content + "%");
                    predicateList.add(pre);
                }


                if (StringUtils.isNotEmpty(mobile)) {
                    Predicate pre = cb.like(root.get("userLogin").get("username").as(String.class), "%" + mobile + "%");
                    predicateList.add(pre);
                }*/

                if (StringUtils.isNotEmpty(content)) {
                    Predicate pre = cb.like(root.get("content").as(String.class), "%" + content + "%");
                    predicateList.add(pre);
                }

                if (StringUtils.isNotEmpty(mobile)) {
                    Predicate pre = cb.like(root.get("userInfo").get("mobile").as(String.class), "%" + mobile + "%");
                    predicateList.add(pre);
                }

                if (category != null) {
                    Predicate pre = cb.equal(root.get("category").get("id").as(Long.class), category);
                    predicateList.add(pre);
                }

                if (status != null) {
                    Predicate pre = cb.equal(root.get("status").as(Integer.class), status);
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
    public PostBase getPostInfo(Long postId, Integer type) {
        return postBaseDao.getPostInfo(postId, type);
    }

    @Override
    public Page<PostBase> findPage(final String name, final Long category, final Integer status, final Integer type, Integer pageNum, Integer pageSize) {

        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<PostBase> page = postBaseDao.findAll(new Specification<PostBase>() {
            @Override
            public Predicate toPredicate(Root<PostBase> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (StringUtils.isNotEmpty(name)) {
                    Predicate pre = cb.like(root.get("name").as(String.class), "%" + name + "%");
                    predicateList.add(pre);
                }

                if (type != null) {
                    Predicate pre = cb.equal(root.get("type").as(Integer.class), type);
                    predicateList.add(pre);
                } else {
                    Predicate pre = cb.ge(root.get("type").as(Integer.class), 0);
                    predicateList.add(pre);

                    Predicate pre2 = cb.le(root.get("type").as(Integer.class), 2);
                    predicateList.add(pre2);

                }

                if (category != null) {
                    Predicate pre = cb.equal(root.get("category").get("id").as(Long.class), category);
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
    public List<PostBase> findByCategoryId(Long categoryId, Integer type) {
        return postBaseDao.findByCategoryId(categoryId,type);
    }

    @Override
    public List<PostBase> findByCategoryId(Long categoryId) {
        return postBaseDao.findByCategoryId(categoryId);
    }

    @Override
    public List<PostBase> findListNew(Integer type) {
        return postBaseDao.findListNew(CommonController.getOldDate(), type);
    }

    @Override
    public List<PostBase> findByType(Integer type) {
        return postBaseDao.findByType(type);
    }

    @Override
    public List<PostBase> findByEssence(Integer essence) {
        return postBaseDao.findByEssence(essence);
    }

    @Override
    public List<PostBase> findByStatus(Integer status, Integer type) {
        return postBaseDao.findByStatus(status, type);
    }
}
