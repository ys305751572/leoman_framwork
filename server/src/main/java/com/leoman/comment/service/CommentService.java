package com.leoman.comment.service;

import com.leoman.comment.entity.Comment;
import com.leoman.common.service.GenericManager;
import com.leoman.post.entity.PostBase;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public interface CommentService extends GenericManager<Comment>{

    public Page<Comment> pageList(String name, String mobile, Integer type, Integer pageNum, Integer pageSize);

    // 根据类型查询新增评论
    public List<Comment> findListNew(Integer type);
}
