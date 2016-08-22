package com.leoman.welfare.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.user.entity.UserInfo;
import com.leoman.welfare.entity.Welfare;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface WelfareDao extends IBaseJpaRepository<Welfare> {

    @Query("select a from Welfare a where a.type = ?1 order by a.createDate desc")
    public List<Welfare> findByType(Integer type);
}
