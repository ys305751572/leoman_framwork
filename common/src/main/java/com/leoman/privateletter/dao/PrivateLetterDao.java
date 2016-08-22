package com.leoman.privateletter.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.privateletter.entity.PrivateLetter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface PrivateLetterDao extends IBaseJpaRepository<PrivateLetter> {

    @Query("select a from PrivateLetter a where a.user.id = ?1 or a.toUser.id = ?1")
    public Page<PrivateLetter> pageAboutMe(Long userId, Pageable pageable);

    @Query("select a from PrivateLetter a where (a.user.id = ?1 and a.toUser.id = ?2) or (a.toUser.id = ?1 and a.user.id = ?2)")
    public List<PrivateLetter> findListSession(Long userId, Long toUserId);
}
