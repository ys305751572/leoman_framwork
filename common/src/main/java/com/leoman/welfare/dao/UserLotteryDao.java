package com.leoman.welfare.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.welfare.entity.UserLottery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface UserLotteryDao extends IBaseJpaRepository<UserLottery> {

    @Query("select a from UserLottery a where a.userInfo.id = ?1")
    public Page<UserLottery> iPageByUserId(Long userId, Pageable pageable);
}
