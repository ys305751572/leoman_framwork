package com.leoman.post.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.post.entity.PostComment;
import com.leoman.post.service.UserChangeService;
import com.leoman.userchange.dao.UserChangeDao;
import com.leoman.userchange.entity.UserChange;
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
public class UserChangeServiceImpl extends GenericManagerImpl<UserChange, UserChangeDao> implements UserChangeService {

    @Autowired
    private UserChangeDao userChangeDao;

    @Override
    public Page<UserChange> page(final String mobile, final Integer type, final Integer status, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<UserChange> page = userChangeDao.findAll(new Specification<UserChange>() {
            @Override
            public Predicate toPredicate(Root<UserChange> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (StringUtils.isNotEmpty(mobile)) {
                    Predicate pre = cb.like(root.get("userInfo").get("mobile").as(String.class), "%" + mobile + "%");
                    predicateList.add(pre);
                }

                if (type != null) {
                    Predicate pre = cb.equal(root.get("category").as(Integer.class), type);
                    predicateList.add(pre);
                }

                if (status != null) {
                    Predicate pre = cb.equal(root.get("type").as(Integer.class), status);
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
