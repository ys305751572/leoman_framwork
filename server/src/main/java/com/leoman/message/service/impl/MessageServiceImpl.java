package com.leoman.message.service.impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.message.dao.MessageDao;
import com.leoman.message.entity.Message;
import com.leoman.message.service.MessageService;
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
 * Created by Administrator on 2016/7/7 0007.
 */
@Service
public class MessageServiceImpl extends GenericManagerImpl<Message, MessageDao> implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Override
    public Page<Message> page(Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Message> page = messageDao.findAll(new Specification<Message>() {
            @Override
            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
