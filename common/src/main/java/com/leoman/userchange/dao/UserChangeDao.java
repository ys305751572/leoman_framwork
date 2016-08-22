package com.leoman.userchange.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.userchange.entity.UserChange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface UserChangeDao extends IBaseJpaRepository<UserChange> {

    @Query("select a from UserChange a where a.sourceId = ?1 and a.type = ?2 and a.userInfo.id = ?3")
    public List<UserChange> findList(Long sourceId, Integer type, Long userId);

    @Query("select a from UserChange a where a.sourceId = ?1 and a.type = ?2")
    public List<UserChange> findList(Long sourceId, Integer type);

    @Query("select a from UserChange a where a.sourceId in (select b.id from PostBase b where b.userInfo.id = ?1 and b.type = 3) and a.type = ?2")
    public Page<UserChange> findListByUserId(Long userId, Integer type, Pageable pageable);

    @Query("select a from UserChange a where a.userInfo.id = ?1 and a.type = ?2")
    public List<UserChange> findListByParams(Long userId, Integer type);

    @Query("select a from UserChange a where a.userInfo.id = ?1 and a.sourceId = ?2 and a.type = ?3")
    public UserChange findOneByParams(Long userId, Long sourceId, Integer type);
}
