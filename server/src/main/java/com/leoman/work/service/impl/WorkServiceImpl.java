package com.leoman.work.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.user.entity.UserInfo;
import com.leoman.work.dao.WorkCreatorDao;
import com.leoman.work.dao.WorkDao;
import com.leoman.work.dao.WorkVideoDao;
import com.leoman.work.entity.Work;
import com.leoman.work.entity.WorkCreator;
import com.leoman.work.entity.WorkVideo;
import com.leoman.work.service.WorkCreatorService;
import com.leoman.work.service.WorkService;
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
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/16 0016.
 */
@Service
public class WorkServiceImpl extends GenericManagerImpl<Work, WorkDao> implements WorkService {

    @Autowired
    private WorkDao workDao;

    @Autowired
    private WorkVideoDao workVideoDao;

    @Override
    public Page<Work> page(final String name, final Long category, final Integer status, final Integer type, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Work> page = workDao.findAll(new Specification<Work>() {
            @Override
            public Predicate toPredicate(Root<Work> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (StringUtils.isNotEmpty(name)) {
                    Predicate pre = cb.like(root.get("name").as(String.class), "%" + name + "%");
                    predicateList.add(pre);
                }

                if (category != null) {
                    Predicate pre = cb.equal(root.get("category").get("id").as(Long.class), category);
                    predicateList.add(pre);
                }

                if (status != null) {
                    Predicate pre = cb.equal(root.get("isEnd").as(Integer.class), status);
                    predicateList.add(pre);
                }

                if (type != null) {
                    Predicate pre = cb.equal(root.get("category").get("type").as(Integer.class), type);
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
    public List<Work> iFindList(Long category) {
        return workDao.iFindList(category);
    }

    @Override
    public List<Work> findByType(Integer type) {
        return workDao.findByType(type);
    }

    @Override
    public Work findBySoHuId(Long sohuId) {
        return workDao.findBySoHuId(sohuId);
    }

    @Override
    public Page<Work> page(final String name, final Long category, final Integer status, final Long souhuId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Work> page = workDao.findAll(new Specification<Work>() {
            @Override
            public Predicate toPredicate(Root<Work> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (StringUtils.isNotEmpty(name)) {
                    Predicate pre = cb.like(root.get("name").as(String.class), "%" + name + "%");
                    predicateList.add(pre);
                }

                if (category != null) {
                    Predicate pre = cb.equal(root.get("category").get("id").as(Long.class), category);
                    predicateList.add(pre);
                }

                if (status != null) {
                    Predicate pre = cb.equal(root.get("isEnd").as(Integer.class), status);
                    predicateList.add(pre);
                }

                //Predicate isEnd = cb.isNotEmpty(root.get("isEnd").as(Long.class));
                //Predicate pre = cb.equal(root.get("isEnd").as(Integer.class), status);
                //predicateList.add(pre);
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

    /*@Override
    public void update(HttpServletRequest request, Long workId, Long workVideoId, Integer num, Integer status) throws UnsupportedEncodingException {
        String msg = request.getParameter("video");
        String msg2 = request.getParameter("videoName");

        String str = new String(msg.getBytes("ISO-8859-1"), "UTF-8");
        String str2 = new String(msg2.getBytes("ISO-8859-1"), "UTF-8");

        WorkVideo workVideo = null;
        if (workVideoId == null) {
            workVideo = new WorkVideo();
            workVideo.setCreateDate(System.currentTimeMillis());
        } else {
            workVideo = workVideoDao.findOne(workVideoId);
        }

        Work work = workDao.findOne(workId);
        if (status == null) {
            work.setIsEnd(0);
        } else {
            work.setIsEnd(status);
        }
        work.setUpdateDate(System.currentTimeMillis());
        workDao.save(work);

        workVideo.setSeries(num);
        workVideo.setLinkUrl(str);
        workVideo.setName(str2);
        workVideo.setWork(work);
        workVideo.setUpdateDate(System.currentTimeMillis());
        workVideoDao.save(workVideo);

    }*/

}
