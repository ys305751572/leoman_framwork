package com.leoman.post.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.post.entity.TpPostSub;
import com.leoman.post.entity.TpPostUser;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface TpPostUserDao extends IBaseJpaRepository<TpPostUser> {

    @Query("select a from TpPostUser a where a.tpPostSub.id = ?1 and a.userInfo.id = ?2")
    public TpPostUser findOneByParams(Long tpPostSubId, Long userId);
}
