package com.leoman.category.service.impl;

import com.leoman.category.dao.CategoryDao;
import com.leoman.category.entity.Category;
import com.leoman.category.service.CategoryService;
import com.leoman.comment.entity.Comment;
import com.leoman.common.service.impl.GenericManagerImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
@Service
public class CategoryServiceImpl extends GenericManagerImpl<Category, CategoryDao> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> findByType(Integer type) {
        return categoryDao.findByType(type);
    }

    @Override
    public List<Category> findType(Integer type) {
        return categoryDao.findType(type);
    }

    @Override
    public List<Category> iFindList() {
        return categoryDao.iFindList();
    }

    @Override
    public Page<Category> page(final Integer type, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Category> page = categoryDao.findAll(new Specification<Category>() {
            @Override
            public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (type != null) {
                    Predicate pre = cb.equal(root.get("type").as(Integer.class), type);
                    predicateList.add(pre);
                }
                else {
                    Predicate pre = cb.ge(root.get("type").as(Integer.class),4);
                    predicateList.add(pre);

                    Predicate pre2 = cb.le(root.get("type").as(Integer.class),6);
                    predicateList.add(pre2);

                }
//                Subquery<Category> subquery = query.subquery(Category.class);
//                Predicate pre = subquery.in(1,2,3);
//                predicateList.add(pre);

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
    public Page<Category> findPage(final Integer type, final Integer status, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Category> page = categoryDao.findAll(new Specification<Category>() {
            @Override
            public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

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
}
