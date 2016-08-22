package com.leoman.post.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.post.entity.PostImage;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface PostImageDao extends IBaseJpaRepository<PostImage> {

    @Query("select a from PostImage a where a.postId = ?1 and a.type = ?2")
    public List<PostImage> findList(Long postId, Integer type);

    @Query("select a from PostImage a where a.id = ?1")
    public PostImage findById(Long postId);
}
