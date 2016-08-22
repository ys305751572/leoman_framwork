package com.leoman.banner.service.impl;

import com.leoman.banner.dao.BannerDao;
import com.leoman.banner.entity.Banner;
import com.leoman.banner.service.BannerService;
import com.leoman.common.service.impl.GenericManagerImpl;
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
 * Created by Administrator on 2016/6/29 0029.
 */
@Service
public class BannerServiceImpl extends GenericManagerImpl<Banner, BannerDao> implements BannerService {

    @Autowired
    private BannerDao bannerDao;

    @Override
    public Page<Banner> page(Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Banner> page = bannerDao.findAll(new Specification<Banner>() {
            @Override
            public Predicate toPredicate(Root<Banner> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
}
