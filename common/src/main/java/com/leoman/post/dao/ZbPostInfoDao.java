package com.leoman.post.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.post.entity.PostBase;
import com.leoman.post.entity.ZbPostInfo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface ZbPostInfoDao extends IBaseJpaRepository<ZbPostInfo> {

    @Query("select a from ZbPostInfo a where a.postBase.id = ?1")
    public List<ZbPostInfo> getPostInfo(Long postId);
}
