package com.leoman.post.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.post.entity.TpPostSub;
import com.leoman.post.entity.ZbPostInfo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface TpPostSubDao extends IBaseJpaRepository<TpPostSub> {

    @Query("select a from TpPostSub a where a.postBase.id = ?1")
    public List<TpPostSub> getPostInfo(Long postId);
}
