package com.leoman.role.service.impl;

import com.leoman.category.entity.Category;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.role.dao.RoleDao;
import com.leoman.role.entity.Role;
import com.leoman.role.service.RoleService;
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
 * Created by Administrator on 2016/7/18 0018.
 */
@Service
public class RoleServiceImpl extends GenericManagerImpl<Role, RoleDao> implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Page<Role> page(Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Role> page = roleDao.findAll(new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                Predicate pre2 = cb.notEqual(root.get("id").as(Long.class), 6);
                predicateList.add(pre2);

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
    public Role getById(Long roleId) {
        return roleDao.findOne(roleId);
    }
}
