package com.leoman.admin.service.impl;

import com.leoman.admin.dao.AdminDao;
import com.leoman.admin.dao.AdminRoleDao;
import com.leoman.admin.entity.Admin;
import com.leoman.admin.entity.AdminRole;
import com.leoman.admin.service.AdminRoleService;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.entity.Constant;
import com.leoman.role.dao.RoleDao;
import com.leoman.utils.Md5Util;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
@Service
public class AdminRoleServiceImpl extends GenericManagerImpl<AdminRole, AdminRoleDao> implements AdminRoleService{

    @Autowired
    private AdminRoleDao adminRoleDao;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public Page<AdminRole> page(final String account, final Long roleId, final String mobile, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<AdminRole> page = adminRoleDao.findAll(new Specification<AdminRole>() {
            @Override
            public Predicate toPredicate(Root<AdminRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (null != account && account != "") {
                    Predicate pre = cb.like(root.get("admin").get("username").as(String.class), "%" + account + "%");
                    predicateList.add(pre);
                }
                if (null != mobile && mobile != "") {
                    Predicate pre = cb.like(root.get("admin").get("mobile").as(String.class), "%" + mobile + "%");
                    predicateList.add(pre);
                }
                if (roleId != null) {
                    Predicate pre = cb.equal(root.get("role").get("id").as(Integer.class), roleId);
                    predicateList.add(pre);
                }

                Predicate pre1 = cb.notEqual(root.get("admin").get("username").as(String.class), "admin");
                predicateList.add(pre1);

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
    public List<AdminRole> findByRoleId(Long roleId) {
        return adminRoleDao.findByRoleId(roleId);
    }

    @Override
    public AdminRole sysUserLogin(HttpSession session, String username, String password) {

        AdminRole adminRole = adminRoleDao.sysUserLogin(username, password);
        if (null != adminRole) {
            // 更新最后一次登录时间
            adminRole.getAdmin().setLastLoginDate(System.currentTimeMillis());
            adminDao.save(adminRole.getAdmin());
            adminRoleDao.save(adminRole);

            putSession(session, adminRole.getId(), adminRole.getAdmin().getUsername());
            return adminRole;
        }

        return null;
    }

    @Override
    public Integer save(HttpServletRequest request, Long id, Long roleId, String account, String mobile, String password) {
        try {

            AdminRole adminRole = null;

            if (null == id) {
                adminRole = new AdminRole();
                Admin admin = new Admin();
                admin.setCreateDate(System.currentTimeMillis());
                admin.setUsername(account);
                admin.setMobile(mobile);
                admin.setPassword(Md5Util.md5(password));
                adminDao.save(admin);

                adminRole.setRole(roleDao.findOne(roleId));
                adminRole.setAdmin(admin);
                adminRole.setCreateDate(System.currentTimeMillis());
                adminRole.setUpdateDate(System.currentTimeMillis());
                adminRoleDao.save(adminRole);
            } else {
                adminRole = adminRoleDao.findOne(id);
                adminRole.getAdmin().setUsername(account);
                adminRole.getAdmin().setMobile(mobile);
                adminRole.getAdmin().setPassword(Md5Util.md5(password));
                adminRole.setRole(roleDao.findOne(roleId));
                adminRole.setUpdateDate(System.currentTimeMillis());
                adminRoleDao.save(adminRole);
            }

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 将登录人的信息放入session中
    private void putSession(HttpSession session, Long id, String name) {
        session.setAttribute(Constant.CURRENT_USER_ID, id);
        session.setAttribute(Constant.CURRENT_USER_NAME, name);
    }
}
