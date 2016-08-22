package com.leoman.report.service.impl;

import com.leoman.common.controller.common.CommonController;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.report.dao.ReportDao;
import com.leoman.report.entity.Report;
import com.leoman.report.service.ReportService;
import com.leoman.resources.dao.StillResourceDao;
import com.leoman.resources.entity.StillResource;
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
 * Created by Administrator on 2016/7/1 0001.
 */
@Service
public class ReportServiceImpl extends GenericManagerImpl<Report, ReportDao> implements ReportService {

    @Autowired
    private ReportDao reportDao;

    @Override
    public Page<Report> page(final String nickname1, final String mobile, final String nickname2, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Report> page = reportDao.findAll(new Specification<Report>() {
            @Override
            public Predicate toPredicate(Root<Report> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (StringUtils.isNotEmpty(nickname1)) {
                    Predicate pre = cb.like(root.get("userInfo").get("nickname").as(String.class), "%" + nickname1 + "%");
                    predicateList.add(pre);
                }

                if (StringUtils.isNotEmpty(mobile)) {
                    Predicate pre = cb.like(root.get("userInfo").get("mobile").as(String.class), "%" + mobile + "%");
                    predicateList.add(pre);
                }

                if (StringUtils.isNotEmpty(nickname2)) {
                    Predicate pre = cb.like(root.get("userPost").get("userInfo").get("nickname").as(String.class), "%" + nickname2 + "%");
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
    public List<Report> findListNew() {
        return reportDao.findListNew(CommonController.getOldDate());
    }
}
