package com.leoman.post.service;

import com.leoman.comment.entity.Comment;
import com.leoman.common.service.GenericManager;
import com.leoman.post.entity.PostBase;
import com.leoman.post.entity.PostComment;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/7/20 0020.
 */
public interface PostCommentService extends GenericManager<PostComment> {

    public Page<PostComment> pageList(String content, String mobile,Integer type, Integer status, Integer pageNum, Integer pageSize);

    public Page<PostComment> page(String name, String mobile,Integer type, Integer status, Integer pageNum, Integer pageSize);

    // 查询新帖子回复
    public List<PostComment> findListNew(Integer type);
}
