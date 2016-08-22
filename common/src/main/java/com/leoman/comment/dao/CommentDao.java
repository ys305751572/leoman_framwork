package com.leoman.comment.dao;

import com.leoman.comment.entity.Comment;
import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.user.entity.UserInfo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface CommentDao extends IBaseJpaRepository<Comment> {

    @Query("select a from Comment a where a.createDate > ?1 and a.type = ?2")
    public List<Comment> findListNew(Long oldDate, Integer type);
}
