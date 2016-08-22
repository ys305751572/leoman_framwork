package com.leoman.feedback.dao;

import com.leoman.comment.entity.Comment;
import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.feedback.entity.Feedback;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface FeedbackDao extends IBaseJpaRepository<Feedback> {

    @Query("select a from Feedback a where a.createDate > ?1")
    public List<Feedback> findListNew(Long oldDate);
}
