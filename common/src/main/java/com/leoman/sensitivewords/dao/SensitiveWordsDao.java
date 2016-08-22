package com.leoman.sensitivewords.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.resources.entity.StillResource;
import com.leoman.sensitivewords.entity.SensitiveWords;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface SensitiveWordsDao extends IBaseJpaRepository<SensitiveWords> {

    @Query("select a from SensitiveWords a where a.code = ?1")
    public List<SensitiveWords> findByCode(String code);
}
